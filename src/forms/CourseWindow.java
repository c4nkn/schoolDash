package forms;

import classes.Course;
import classes.Faculty;
import classes.Programs;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import data.DataHandler;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CourseWindow extends JFrame {
    private JPanel CourseWindow;
    private JTextField courseIdTF;
    private JComboBox facultyCB;
    private JTextField lecturerTF;
    private JButton addCourseButton;
    private JButton clearButton;
    private JLabel courseIdLabel;
    private JLabel facilityTF;
    private JLabel departmentLabel;
    private JComboBox departmentCB;
    private JLabel courseNameLabel;
    private JTextField courseNameTF;
    private JLabel lecturerLabel;

    Faculty faculty = new Faculty();
    String facultyName = faculty.facultyName;

    Programs programs = new Programs();
    String programName = programs.programName;

    public CourseWindow() {
        setTitle("Add New Course");
        setDefaultCloseOperation(HIDE_ON_CLOSE);
        setSize(400, 400);
        setLocation(550,250);
        setResizable(false);
        setContentPane(CourseWindow);

        var facultyNames = DataHandler.getFacultyNames();
        for (String name : facultyNames) {
            facultyCB.addItem(name);
        }
        facultyCB.setSelectedIndex(-1);

        if (facultyCB.getSelectedIndex() == -1) {
            departmentCB.setEnabled(false);
        }

        if (!departmentCB.isEnabled()) {
            departmentCB.setToolTipText("You have to choose faculty for see departments.");
        }

        facultyCB.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (facultyCB.getSelectedIndex() != -1) {
                    departmentCB.setEnabled(true);
                    departmentCB.removeAllItems();
                    departmentCB.setSelectedIndex(-1);

                    var programNames = DataHandler.getProgramNames(facultyCB.getSelectedItem().toString());
                    for (String programName : programNames) {
                        departmentCB.addItem(programName);
                    }
                }
            }
        });

        departmentCB.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });

        addCourseButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (courseIdTF.getText() == null || courseNameTF.getText() == null || lecturerTF.getText() == null || facultyCB.getSelectedItem() == null || departmentCB.getSelectedItem() == null) {
                    JOptionPane.showMessageDialog(null, "Please fill in all blank areas!", "Blank Areas", JOptionPane.WARNING_MESSAGE);
                } else {
                    facultyName = facultyCB.getSelectedItem().toString();
                    programName = departmentCB.getSelectedItem().toString();

                    Course newCourse = new Course();
                    newCourse.courseCode = courseIdTF.getText();
                    newCourse.courseName = courseNameTF.getText();
                    newCourse.lecturer = lecturerTF.getText();

                    var jsonContent = DataHandler.readJsonFile("src/data/Courses.json");

                    for (JsonElement facultyElement : jsonContent) {
                        JsonObject facultyObject = facultyElement.getAsJsonObject();
                        JsonArray facultiesArray = facultyObject.getAsJsonArray("faculties");

                        for (JsonElement faculty : facultiesArray) {
                            JsonObject facultyJson = faculty.getAsJsonObject();
                            String currentFacultyName = facultyJson.get("facultyName").getAsString();

                            if (currentFacultyName.equals(facultyName)) {
                                JsonArray programsArray = facultyJson.getAsJsonArray("programs");

                                for (JsonElement program : programsArray) {
                                    JsonObject programJson = program.getAsJsonObject();
                                    String currentProgramName = programJson.get("programName").getAsString();

                                    if (currentProgramName.equals(programName)) {
                                        JsonArray coursesArray = programJson.getAsJsonArray("courses");
                                        coursesArray.add(new Gson().toJsonTree(newCourse));

                                        DataHandler.saveJsonData("src/data/Courses.json", jsonContent);
                                        JOptionPane.showMessageDialog(null, "Course added successfully!", "Successful Task", JOptionPane.INFORMATION_MESSAGE);
                                    }
                                }
                            }
                        }
                    }
                }
            }
        });

        clearButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                courseIdTF.setText("");
                courseNameTF.setText("");
                lecturerTF.setText("");

                facultyCB.setSelectedIndex(-1);
                departmentCB.setSelectedIndex(-1);
                departmentCB.removeAllItems();
            }
        });

        setVisible(true);
    }

    public static void main(String[] args) {
        new CourseWindow();
    }
}

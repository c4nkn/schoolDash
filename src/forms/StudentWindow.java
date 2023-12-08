package forms;

import classes.Course;
import classes.Faculty;
import classes.Programs;
import classes.Student;
import com.google.gson.*;
import data.DataHandler;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class StudentWindow extends JFrame {
    private JPanel StudentWindow;
    private JTextField studentNoTF;
    private JTextField studentNameTF;
    private JLabel studentNumberLabel;
    private JLabel studentNameLabel;
    private JTextField studentSurnameTF;
    private JComboBox facultyCB;
    private JComboBox departmentCB;
    private JButton addStudentButton;
    private JButton clearButton;
    private JLabel departLabel;
    private JLabel facultyLabel;
    private JLabel surnameLabel;
    private JLabel courseLabel;
    private JComboBox courseCB;

    Faculty faculty = new Faculty();
    String facultyName = faculty.facultyName;

    Programs programs = new Programs();
    String programName = programs.programName;

    private int newID;
    String selectedCourseCode;
    String selectedLecturer;

    public StudentWindow() {
        setTitle("Add New classes.Student");
        setDefaultCloseOperation(HIDE_ON_CLOSE);
        setSize(400, 500);
        setLocation(550,250);
        setResizable(false);
        setContentPane(StudentWindow);

        newID = createStudentNumber();
        studentNoTF.setText(String.valueOf(newID));

        var facultyNames = DataHandler.getFacultyNames();
        for (String name : facultyNames) {
            facultyCB.addItem(name);
        }
        facultyCB.setSelectedIndex(-1);

        if (facultyCB.getSelectedIndex() == -1) {
            departmentCB.setEnabled(false);
            courseCB.setEnabled(false);
        }

        if (!departmentCB.isEnabled()) {
            departmentCB.setToolTipText("You have to choose faculty for see departments.");
            courseCB.setEnabled(false);
        }

        if (departmentCB.getSelectedIndex() == -1) {
            courseCB.setEnabled(false);
        }

        facultyCB.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (facultyCB.getSelectedIndex() != -1) {
                    departmentCB.setEnabled(true);
                    departmentCB.removeAllItems();

                    var programNames = DataHandler.getProgramNames(facultyCB.getSelectedItem().toString());
                    for (String programName : programNames) {
                        departmentCB.addItem(programName);
                    }

                    departmentCB.setSelectedIndex(-1);
                    courseCB.setEnabled(false);
                    courseCB.removeAllItems();
                }
            }
        });

        departmentCB.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (departmentCB.getSelectedIndex() != -1) {
                    courseCB.setEnabled(true);
                    courseCB.removeAllItems();
                    courseCB.setSelectedIndex(-1);

                    List<List<String>> courseList = DataHandler.getCourses(facultyCB.getSelectedItem().toString(), departmentCB.getSelectedItem().toString());

                    List<String> courseCodeList = courseList.get(0);
                    List<String> courseNameList = courseList.get(1);
                    List<String> lecturerList = courseList.get(2);

                    for (String name : courseNameList) {
                        courseCB.addItem(name);
                    }

                    int selectedIndex = courseCB.getSelectedIndex();
                    selectedCourseCode = courseCodeList.get(selectedIndex);
                    selectedLecturer = lecturerList.get(selectedIndex);
                }
            }
        });

        addStudentButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (studentNameTF.getText() == null || studentSurnameTF.getText() == null || facultyCB.getSelectedItem() == null || departmentCB.getSelectedItem() == null || courseCB.getSelectedItem() == null) {
                    JOptionPane.showMessageDialog(null, "Please fill in all blank areas!", "Blank Areas", JOptionPane.WARNING_MESSAGE);
                } else {
                    facultyName = facultyCB.getSelectedItem().toString();
                    programName = departmentCB.getSelectedItem().toString();

                    var JsonContent = DataHandler.readJsonFile("src/data/Students.json");

                    Course newCourse = new Course();
                    newCourse.courseCode = selectedCourseCode;
                    newCourse.courseName = courseCB.getSelectedItem().toString();
                    newCourse.lecturer = selectedLecturer;
                    Course[] coursesArray = { newCourse };

                    Student newStudent = new Student();
                    newStudent.studentNumber = newID;
                    newStudent.studentName = studentNameTF.getText();
                    newStudent.studentSurname = studentSurnameTF.getText();
                    newStudent.faculty = facultyName;
                    newStudent.program = programName;
                    newStudent.courses = coursesArray;

                    JsonContent.add(new Gson().toJsonTree(newStudent));
                    DataHandler.saveJsonData("src/data/Students.json", JsonContent);

                    newID = createStudentNumber();
                    studentNoTF.setText(String.valueOf(newID));
                }
            }
        });

        clearButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                newID = createStudentNumber();
                studentNoTF.setText(String.valueOf(newID));

                studentNameTF.setText("");
                studentSurnameTF.setText("");

                facultyCB.setSelectedIndex(-1);

                departmentCB.setSelectedIndex(-1);
                departmentCB.removeAllItems();
                departmentCB.setEnabled(false);

                courseCB.setSelectedIndex(-1);
                courseCB.removeAllItems();
                courseCB.setEnabled(false);
            }
        });

        setVisible(true);
    }

    private static int createStudentNumber() {
        JsonArray JsonContent = DataHandler.readJsonFile("src/data/Students.json");

        int maxNumber = 0;

        for (JsonElement student : JsonContent) {
            JsonObject studentObj = student.getAsJsonObject();
            int studentNumber = studentObj.getAsJsonPrimitive("studentNumber").getAsInt();

            if (studentNumber > maxNumber) {
                maxNumber = studentNumber;
            }
        }

        return maxNumber + 1;
    }

    public static void main(String[] args) {
        new StudentWindow();
    }
}

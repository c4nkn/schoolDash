import javax.swing.*;

public class Course extends JFrame {
    private JPanel CourseWindow;
    private JTextField courseCodeTF;
    private JComboBox facilityCB;
    private JRadioButton a1stRadioButton;
    private JRadioButton a4thRadioButton;
    private JTextField lecturerTF;
    private JButton addCourseButton;
    private JButton clearButton;
    private JLabel courseCodeLabel;
    private JLabel facilityTF;
    private JLabel departmentLabel;
    private JComboBox departmentCB;
    private JLabel academicYearLabel;
    private JRadioButton a2ndRadioButton;
    private JRadioButton a3rdRadioButton;
    private JLabel courseNameLabel;
    private JTextField courseNameTF;
    private JLabel semesterLabel;
    private JRadioButton fall2023RB;
    private JRadioButton spring2024RB;
    private JLabel lecturerLabel;

    public Course() {
        setTitle("Add Course");
        setSize(400, 450);
        setResizable(false);
        setLocation(200,200);
        setContentPane(CourseWindow);
        setVisible(true);
    }

    public static void main(String[] args) {
        new Course();
    }
}

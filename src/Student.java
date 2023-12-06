import javax.swing.*;

public class Student extends JFrame {
    private JPanel StudentWindow;
    private JTextField studentNoTF;
    private JTextField studentNameTF;
    private JLabel studentNumberLabel;
    private JLabel studentNameLabel;
    private JTextField studentSurnameTF;
    private JComboBox facultyCB;
    private JRadioButton a1stRadioButton;
    private JRadioButton a2ndRadioButton;
    private JRadioButton a3rdRadioButton;
    private JRadioButton a4thRadioButton;
    private JComboBox departmentCB;
    private JButton addStudentButton;
    private JButton clearButton;
    private JLabel yearLabel;
    private JLabel departLabel;
    private JLabel facultyLabel;
    private JLabel surnameLabel;

    public Student() {
        setTitle("Add Student");
        setSize(400, 450);
        setResizable(false);
        setLocation(200,200);
        setContentPane(StudentWindow);

        if (!departmentCB.isEnabled()) {
            departmentCB.setToolTipText("You have to choose faculty for see departments.");
        }

        if (departmentCB.getSelectedIndex() == -1) {
            ButtonGroup buttonGroup = new ButtonGroup();
            buttonGroup.add(a1stRadioButton);
            buttonGroup.add(a2ndRadioButton);
            buttonGroup.add(a3rdRadioButton);
            buttonGroup.add(a4thRadioButton);

            java.util.Enumeration<AbstractButton> buttons = buttonGroup.getElements();

            while (buttons.hasMoreElements()) {
                buttons.nextElement().setEnabled(false);
            }
        }

        setVisible(true);
    }

    public static void main(String[] args) {
        new Student();
    }
}

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MenuWindow extends JFrame {
    private JPanel MainPanel;
    private JPanel ActionPanel;
    private JLabel image;
    private JButton addStudentButton;
    private JButton addCourseButton;

    public MenuWindow() {
        setTitle("School Dashboard");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(400, 400);
        setLocation(500,200);
        setResizable(false);
        setContentPane(MainPanel);

        addStudentButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                StudentWindow studentWindow = new StudentWindow();
                studentWindow.setVisible(true);
            }
        });

        addCourseButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CourseWindow courseWindow = new CourseWindow();
                courseWindow.setVisible(true);
            }
        });

        setVisible(true);
    }

    public static void main(String[] args) {
        new MenuWindow();
    }
}

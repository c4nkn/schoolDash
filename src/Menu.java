import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Menu extends JFrame {
    private JPanel MainPanel;
    private JPanel ActionPanel;
    private JLabel image;
    private JButton addStudentButton;
    private JButton addCourseButton;

    public Menu() {
        setTitle("School Dashboard");
        setSize(400, 400);
        setResizable(false);
        setLocation(200,200);
        setContentPane(MainPanel);

        addStudentButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });

        addCourseButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
    }

    public static void main(String[] args) {
        new Menu();
    }
}

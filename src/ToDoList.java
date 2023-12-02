import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ToDoList extends JFrame{
    private JPanel panel1;
    private JLabel timeLabel;

    public ToDoList() {
        int delay = 100;
        Timer timer = new Timer(delay, new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                LocalDateTime now = LocalDateTime.now();
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("hh:mm:ss a");
                String formattedDateTime = now.format(formatter);
                timeLabel.setText(formattedDateTime);
            }
        });
 
        timer.start();
    }

    public static void main(String[] args) {
        ToDoList app = new ToDoList();
        app.setContentPane(app.panel1);
        JButton btn = new JButton("HIEFS");
        app.setSize(1000, 600);
        app.setDefaultCloseOperation(EXIT_ON_CLOSE);
        app.setTitle("Hello World");
        app.setVisible(true);
    }
}

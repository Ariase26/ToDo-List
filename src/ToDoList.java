import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ToDoList extends JFrame{
    private JPanel panel1;
    private JLabel timeLabel;
    private JLabel dateLabel;
    private JList todoList;
    private JTextArea notepadText;
    private JButton addTaskButton;
    private JList prioTodoList;

    public ToDoList() {
        int delay = 100;

        Timer timer = new Timer(delay, e -> {
            LocalDateTime now = LocalDateTime.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("hh:mm:ss a");
            String formattedDateTime = now.format(formatter); //gihimo into string
            timeLabel.setText(formattedDateTime); //butang sa text label ang time
        });

        timer.start();

        addTaskButton.addActionListener(e -> {
            TaskDialog task = new TaskDialog();

            task.setSize(300,300);
            task.setVisible(true);
        });
    }

    public static void main(String[] args) {
        ToDoList app = new ToDoList();
        app.setContentPane(app.panel1);
        app.setSize(800, 600);
        app.setDefaultCloseOperation(EXIT_ON_CLOSE);
        app.setTitle("Hello World");
        app.setVisible(true);
    }


}

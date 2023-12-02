import javax.swing.*;
import java.awt.event.*;

public class TaskDialog extends JDialog {
    private JPanel contentPane;
    private JButton OKButton;
    private JButton cancelButton;
    private JTextField textField1;

    // Add a field to store the task
    private ToDoList.YourTaskClass task;

    public TaskDialog() {
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(OKButton);

        OKButton.addActionListener(e -> onOK());

        cancelButton.addActionListener(e -> onCancel());

        // call onCancel() when the cross is clicked
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });

        // call onCancel() on ESCAPE
        contentPane.registerKeyboardAction(e -> onCancel(), KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
    }

    private void onOK() {
        // Validate the input, create the task, and set it
        String taskName = textField1.getText().trim();
        if (!taskName.isEmpty()) {
            // Assuming YourTaskClass has a constructor that takes a task name
            task = new ToDoList.YourTaskClass(taskName, "");
            dispose();
        } else {
            JOptionPane.showMessageDialog(this, "Please enter a task name.");
        }
    }

    private void onCancel() {
        // set task to null when canceled
        task = null;
        dispose();
    }

    // Getter method for retrieving the task
    public ToDoList.YourTaskClass getTask() {
        return task;
    }

    public static void main(String[] args) {
        TaskDialog dialog = new TaskDialog();
        dialog.pack();
        dialog.setVisible(true);
        System.exit(0);
    }
}
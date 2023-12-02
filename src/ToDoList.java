import javax.swing.*;
import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ToDoList extends JFrame {
    private JPanel panel1;
    private JLabel timeLabel;
    private JLabel dateLabel;
    private JList<YourTaskClass> todoList;
    private JTextArea notepadText;
    private JButton btAdd;
    private JList<YourTaskClass> priorTodoList;
    private JButton btDelete;

    public ToDoList() {
        int delay = 1000; // Update every second

        Timer timer = new Timer(delay, e -> {
            LocalDateTime now = LocalDateTime.now();
            DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("hh:mm:ss a");
            DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

            String formattedTime = now.format(timeFormatter);
            String formattedDate = now.format(dateFormatter);

            timeLabel.setText(formattedTime);
            dateLabel.setText(formattedDate);
        });

        timer.start();

        // Add task pop-up
        btAdd.addActionListener(e -> {
            TaskDialog taskDialog = new TaskDialog();
            taskDialog.setSize(300, 300);
            taskDialog.setVisible(true);

            // Assuming TaskDialog has a method to get the created task
            YourTaskClass newTask = taskDialog.getTask();
            if (newTask != null) {
                // Add the new task to the todoList
                DefaultListModel<YourTaskClass> model = (DefaultListModel<YourTaskClass>) todoList.getModel();
                model.addElement(newTask);

                // Save updated task list to file
                saveTasksToFile();
            }
        });

        // Delete task button
        btDelete.addActionListener(e -> {
            int selectedIndex = todoList.getSelectedIndex();
            if (selectedIndex != -1) {
                DefaultListModel<YourTaskClass> model = (DefaultListModel<YourTaskClass>) todoList.getModel();
                model.remove(selectedIndex);

                // Save updated task list to file
                saveTasksToFile();
            } else {
                JOptionPane.showMessageDialog(this, "Please select a task to delete.");
            }
        });

        // Add a ListSelectionListener to the todoList
        todoList.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                // Get the selected task
                YourTaskClass selectedTask = todoList.getSelectedValue();
                if (selectedTask != null) {
                    // Display the task description in notepadText
                    notepadText.setText(selectedTask.getTaskDescription());
                }
            }
        });

        // Load tasks from file when the application starts
        loadTasksFromFile();
    }

    // Method to load tasks from a file using BufferedReader
    private void loadTasksFromFile() {
        DefaultListModel<YourTaskClass> model = (DefaultListModel<YourTaskClass>) todoList.getModel();
        model.clear(); // Clear the existing tasks in the model

        try (BufferedReader reader = new BufferedReader(new FileReader("tasks.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                // Split the line into taskName and taskDescription
                String[] parts = line.split(",");
                if (parts.length >= 1) {
                    String taskName = parts[0];
                    String taskDescription = (parts.length >= 2) ? parts[1] : "";
                    YourTaskClass task = new YourTaskClass(taskName, taskDescription);
                    model.addElement(task);
                }
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Error loading tasks from file.");
        }
    }

    // Method to save tasks to a file using BufferedWriter
    private void saveTasksToFile() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("tasks.txt"))) {
            DefaultListModel<YourTaskClass> model = (DefaultListModel<YourTaskClass>) todoList.getModel();
            for (int i = 0; i < model.getSize(); i++) {
                YourTaskClass task = model.getElementAt(i);
                writer.write(task.getTaskName() + "," + task.getTaskDescription());
                writer.newLine();
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Error saving tasks to file.");
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            ToDoList app = new ToDoList();
            app.setContentPane(app.panel1);
            app.setSize(800, 600);
            app.setDefaultCloseOperation(EXIT_ON_CLOSE);
            app.setTitle("To-Do List App");
            app.setVisible(true);
        });
    }

    public static class YourTaskClass {
        private String taskName;
        private String taskDescription;

        public YourTaskClass(String taskName, String taskDescription) {
            this.taskName = taskName;
            this.taskDescription = taskDescription;
        }

        public String getTaskName() {
            return taskName;
        }

        public void setTaskName(String taskName) {
            this.taskName = taskName;
        }

        public String getTaskDescription() {
            return taskDescription;
        }

        public void setTaskDescription(String taskDescription) {
            this.taskDescription = taskDescription;
        }

        @Override
        public String toString() {
            return taskName; // Customize this to display task details as needed
        }
    }
}

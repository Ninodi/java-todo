package org.example.todoapp;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.example.todoapp.model.Task;
import org.example.todoapp.persistence.TaskStorage;
import org.example.todoapp.ui.TaskCell;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;

import java.time.LocalDate;

public class Main extends Application {
    private final HBox header = new HBox();
    private final ObjectProperty<Task> editingTask =
            new SimpleObjectProperty<>();

    @Override
    public void start(Stage stage) {

        TaskStorage storage = new TaskStorage();
        // Text field where the user types a task
        TextField taskInput = new TextField();
        taskInput.setPromptText("Enter a task...");

        ComboBox<String> categoryBox = new ComboBox<>();
        categoryBox.getItems().addAll(
                "University",
                "Work",
                "Personal"
        );

        categoryBox.setValue("Personal");

        DatePicker dueDatePicker = new DatePicker();
        dueDatePicker.setValue(LocalDate.now());
//        dueDatePicker.setPromptText("Choose due date");

        // Button for adding the task
        Button addButton = new Button("Add Task");

        // List that will display our tasks
        ListView<Task> taskList = new ListView<>();

        taskList.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldTask, newTask) -> {

                    if (editingTask.get() != null && newTask != editingTask.get()) {
                        taskList.getSelectionModel().select(editingTask.get());
                    }
                }
        );

        taskList.setCellFactory(list -> {
            TaskCell cell = new TaskCell();

            cell.setEditingTaskProperty(editingTask);

            cell.setOnTaskChanged(() -> {
                storage.saveTasks(taskList.getItems());
            });

            cell.setOnTaskDeleted(task -> {
                taskList.getItems().remove(task);
                storage.saveTasks(taskList.getItems());
            });

            cell.setOnEditRequested(task -> {
                editingTask.set(task);

                taskInput.setText(task.getTitle());
                categoryBox.setValue(task.getCategory());
                dueDatePicker.setValue(task.getDueDate());

                addButton.setText("Update Task");
            });
            return cell;
        });

        taskList.getItems().addAll(storage.loadTasks());

        // What happens when the button is clicked
        addButton.setOnAction(event -> {

            String title = taskInput.getText();
            String category = categoryBox.getValue();
            LocalDate dueDate = dueDatePicker.getValue();

            if (!title.isEmpty() && dueDate != null) {

                if (editingTask.get() == null) {

                    // ADD MODE
                    Task task = new Task(title, category, dueDate);
                    taskList.getItems().add(task);

                } else {

                    // EDIT MODE
                    Task task = editingTask.get();

                    task.setTitle(title);
                    task.setCategory(category);
                    task.setDueDate(dueDate);

                    taskList.refresh();

                    editingTask.set(null);
                    taskList.getSelectionModel().clearSelection();
                }

                storage.saveTasks(taskList.getItems());

                taskInput.clear();
                categoryBox.setValue("Personal");
                dueDatePicker.setValue(null);

                addButton.setText("Add Task");
            }
        });


        // Arrange everything vertically
        VBox layout = new VBox(10);
        header.getChildren().addAll(taskInput, categoryBox, dueDatePicker, addButton);
        header.setSpacing(10);
        layout.getChildren().addAll(
                header,
                taskList
        );

        // Create the scene
        Scene scene = new Scene(layout, 600, 600);

        // Configure the window
        stage.setTitle("Todo App");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
package org.example.todoapp.ui;

import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import org.example.todoapp.model.Task;
import javafx.scene.text.Text;
import javafx.beans.property.ObjectProperty;
import java.util.function.Consumer;

public class TaskCell extends ListCell<Task> {

    private final HBox box = new HBox();
    private final HBox actionsBox = new HBox();
    private final VBox dataBox = new VBox();
    private final Text titleText = new Text();
    private final Label detailsLabel = new Label();
    private final CheckBox checkBox = new CheckBox();
    private final Button deleteBtn = new Button("Delete");
    private final Button editBtn = new Button("Edit");
    private Runnable onTaskChanged;
    private Consumer<Task> onTaskDeleted;
    private Consumer<Task> onEditRequested;
    private Task editingTask;

    public TaskCell() {
        dataBox.getChildren().addAll(titleText, detailsLabel);
        actionsBox.getChildren().addAll(checkBox, deleteBtn, editBtn);
        box.getChildren().addAll(dataBox, actionsBox);
        box.setSpacing(10);
        actionsBox.setSpacing(10);

        checkBox.setOnAction(event -> {
            Task task = getItem();
            if (task != null) {
                task.setCompleted(checkBox.isSelected());

                titleText.setStrikethrough(task.isCompleted());

                updateDetails(task);

                if (onTaskChanged != null) {
                    onTaskChanged.run();
                }
            }
        });

        deleteBtn.setOnAction(event -> {
            Task task = getItem();
            if (task != null && onTaskDeleted != null) {
                onTaskDeleted.accept(task);
            }
        });

        editBtn.setOnAction(event -> {
            Task task = getItem();

            if (task != null && onEditRequested != null) {
                onEditRequested.accept(task);
            }
        });
    }


    public void setOnTaskChanged(Runnable onTaskChanged) {
        this.onTaskChanged = onTaskChanged;
    }
    public void setOnTaskDeleted(Consumer<Task> onTaskDeleted) {
        this.onTaskDeleted = onTaskDeleted;
    }
    public void setOnEditRequested(Consumer<Task> onEditRequested) {
        this.onEditRequested = onEditRequested;
    }

    private void updateDetails(Task task) {
        detailsLabel.setText(
                task.getCategory()
                        + " • Due: "
                        + task.getDueDate()
                        + " • "
                        + (task.isCompleted() ? "Completed" : "Incomplete")
        );
    }

    private void updateButtonState() {
        boolean isEditing = editingTask != null;
        boolean isThisTask = getItem() == editingTask;

        checkBox.setDisable(isEditing && !isThisTask);
        deleteBtn.setDisable(isEditing && !isThisTask);
        editBtn.setDisable(isEditing && !isThisTask);
    }

    public void setEditingTask(Task editingTask) {
        this.editingTask = editingTask;
        updateButtonState();
    }

    public void setEditingTaskProperty(ObjectProperty<Task> editingTaskProperty) {
        editingTaskProperty.addListener((observable, oldTask, newTask) -> {
            setEditingTask(newTask);
        });

        setEditingTask(editingTaskProperty.get());
    }

    @Override
    protected void updateItem(Task task, boolean empty) {
        super.updateItem(task, empty);

        if (empty || task == null) {
            setGraphic(null);
        } else {
            titleText.setText(task.getTitle());

            checkBox.setSelected(task.isCompleted());

            titleText.setStrikethrough(task.isCompleted());

            updateDetails(task);

            setGraphic(box);

            updateButtonState();
        }
    }
}
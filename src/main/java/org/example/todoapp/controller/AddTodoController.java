package org.example.todoapp.controller;

import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import org.example.todoapp.database.TodoService;
import org.example.todoapp.navigation.AppPage;
import org.example.todoapp.navigation.AppRouter;

import javafx.event.ActionEvent;
import java.time.LocalDate;
import java.util.Date;

public class AddTodoController {

    private AppRouter router;
    private TodoService todoService;

    @FXML
    private TextField titleField;

    @FXML
    private TextArea descriptionField;

    @FXML
    private DatePicker dueDateField;

    @FXML
    private void handleDateSelection(ActionEvent event) {
        LocalDate selectedDate = dueDateField.getValue();

        if (selectedDate != null) {
            System.out.println("Selected Date: " + selectedDate);
        } else {
            System.out.println("No date was selected.");
        }
    }

    public void setTodoService(TodoService todoService) {

        this.todoService = todoService;
    }

    public void setRouter(AppRouter router) {
        this.router = router;
    }

    @FXML
    public void handleBack() {
        router.navigateTo(AppPage.DASHBOARD);
    }

    @FXML
    public void handleSave() throws Exception {
        String titleInput = titleField.getText();
        String descriptionInput = descriptionField.getText();
        LocalDate localDate = dueDateField.getValue();
        todoService.createTodo(titleInput, descriptionInput, localDate);
        handleBack();
    }
}
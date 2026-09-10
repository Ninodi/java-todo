package org.example.todoapp.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.example.todoapp.database.CategoriesService;

public class AddCategoryController {
    private Runnable onClose;
    private CategoriesService categoriesService;
    private Runnable onCategoryCreated;

    public void setOnCategoryCreated(Runnable onCategoryCreated) {
        this.onCategoryCreated = onCategoryCreated;
    }

    @FXML
    private TextField titleField;

    @FXML
    private ColorPicker colorPicker;

    public void setCategoriesService(CategoriesService categoriesService) {
        this.categoriesService = categoriesService;
    }

    @FXML
    private void handleCreate() {
        String title = titleField.getText();
        String color = colorPicker.getValue().toString();

        try {
            categoriesService.createCategory(title, color);

            if (onCategoryCreated != null) {
                onCategoryCreated.run();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setOnClose(Runnable onClose) {
        this.onClose = onClose;
    }

    @FXML
    private void handleCancel() {
        if (onClose != null) {
            onClose.run();
        }
    }
}
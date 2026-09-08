package org.example.todoapp.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.layout.StackPane;
import org.example.todoapp.auth.AuthManager;

import java.io.IOException;

public class DashboardController {

    private AuthManager authManager;

    @FXML
    private StackPane contentArea;


    public void setAuthManager(AuthManager authManager) {
        this.authManager = authManager;
    }


    @FXML
    public void initialize() {
        showTodos();
    }


    @FXML
    public void showTodos() {
        loadView("/todoapp/ui/TodoView.fxml");
    }


    @FXML
    public void showCategories() {
        loadView("/todoapp/ui/CategoriesView.fxml");
    }


    @FXML
    public void showProfile() {
        loadView("/todoapp/ui/ProfileView.fxml");
    }


    private void loadView(String fxml) {

        try {
            Node view = FXMLLoader.load(
                    getClass().getResource(fxml)
            );

            contentArea.getChildren().setAll(view);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @FXML
    public void handleLogout() {
        authManager.logout();
    }
}
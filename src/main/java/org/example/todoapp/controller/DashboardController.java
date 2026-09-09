package org.example.todoapp.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.layout.StackPane;
import org.example.todoapp.auth.AuthManager;
import javafx.scene.control.Button;
import org.example.todoapp.navigation.AppPage;
import org.example.todoapp.navigation.AppRouter;
import java.util.List;
import java.io.IOException;

public class DashboardController {

    private AuthManager authManager;
    private AppRouter router;

    public void setRouter(AppRouter router) {
        this.router = router;
    }

    @FXML
    private StackPane contentArea;


    public void setAuthManager(AuthManager authManager) {
        this.authManager = authManager;
    }


    @FXML
    private Button todosButton;

    @FXML
    private Button categoriesButton;

    @FXML
    private Button profileButton;

    @FXML
    private Button addTodoButton;

    private List<Button> navigationButtons;

    @FXML
    public void initialize() {

        navigationButtons = List.of(
                todosButton,
                categoriesButton,
                profileButton
        );

        showTodos();
    }

    @FXML
    public void showTodos() {
        loadView("/todoapp/ui/TodoView.fxml");
        setActiveButton(todosButton);
    }


    @FXML
    public void showCategories() {
        loadView("/todoapp/ui/CategoriesView.fxml");
        setActiveButton(categoriesButton);
    }


    @FXML
    public void showProfile() {
        loadView("/todoapp/ui/ProfileView.fxml");
        setActiveButton(profileButton);
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

    private void setActiveButton(Button activeButton) {

        navigationButtons.forEach(button ->
                button.getStyleClass().remove("active")
        );

        activeButton.getStyleClass().add("active");
    }

    @FXML
    public void showAddTodoView() {
        router.navigateTo(AppPage.ADD_TODO);
    }
}
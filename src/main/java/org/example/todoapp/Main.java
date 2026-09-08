package org.example.todoapp;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.example.todoapp.auth.AuthManager;
import org.example.todoapp.controller.LoginController;
import org.example.todoapp.controller.DashboardController;

import java.io.IOException;

public class Main extends Application {

    private AuthManager authManager;

    @Override
    public void start(Stage stage) {

        authManager = new AuthManager();

        authManager.getSession()
                .loggedInProperty()
                .addListener((obs, oldValue, newValue) -> {
                    updatePage(stage);
                });

        updatePage(stage);
    }

    private void updatePage(Stage stage) {

        try {
            if (authManager.isLoggedIn()) {

                loadPage(
                        stage,
                        "/todoapp/ui/dashboard-view.fxml",
                        "Dashboard"
                );

            } else {

                loadPage(
                        stage,
                        "/todoapp/ui/login-view.fxml",
                        "Login"
                );
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadPage(Stage stage, String viewLink, String title) throws IOException {
        FXMLLoader loader = new FXMLLoader(
                getClass().getResource(viewLink)
        );


        Parent root = loader.load();

        if (viewLink.equals("/todoapp/ui/login-view.fxml")) {

            LoginController controller = loader.getController();
            controller.setAuthManager(authManager);

        } else if (viewLink.equals("/todoapp/ui/dashboard-view.fxml")) {

            DashboardController controller = loader.getController();
            controller.setAuthManager(authManager);
        }

        Scene scene = new Scene(root, 800, 600);

        stage.setTitle("Todo App - " + title);
        stage.setScene(scene);
        stage.show();
    }
    public AuthManager getAuthManager() {
        return authManager;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
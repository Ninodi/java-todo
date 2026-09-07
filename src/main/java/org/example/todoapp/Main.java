package org.example.todoapp;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import org.example.todoapp.auth.AuthManager;

import java.io.IOException;

public class Main extends Application {

    private AuthManager authManager;

    @Override
    public void start(Stage stage) throws IOException {
        authManager = new AuthManager();

        if (authManager.isLoggedIn()) {
//            showTodoPage(stage);
            loadPage(stage, "/todoapp/ui/todo-view.fxml", "Todo List");
        } else {
//            showLoginPage(stage);
            loadPage(stage, "/todoapp/ui/login-view.fxml", "Login");
        }
    }

    private void showLoginPage(Stage stage) throws IOException {

        FXMLLoader loader = new FXMLLoader(
                getClass().getResource(
                        "/todoapp/ui/login-view.fxml"
                )
        );

        Parent root = loader.load();

        Scene scene = new Scene(root, 800, 600);

        stage.setTitle("Todo App - Login");
        stage.setScene(scene);
        stage.show();
    }

    private void showTodoPage(Stage stage) throws IOException{

        FXMLLoader loader = new FXMLLoader(
                getClass().getResource(
                        "/todoapp/ui/login-view.fxml"
                )
        );

        Parent root = loader.load();

        Scene scene = new Scene(root, 800, 600);

        stage.setTitle("Todo App - Login");
        stage.setScene(scene);
        stage.show();
    }

    private void loadPage(Stage stage, String viewLink, String title) throws IOException {
        FXMLLoader loader = new FXMLLoader(
                getClass().getResource(viewLink)
        );

        Parent root = loader.load();

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
package org.example.todoapp.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.example.todoapp.auth.AuthManager;

import java.io.IOException;

public class LoginController {
    private final AuthManager authManager = new AuthManager();
    @FXML
    private TextField emailField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Button loginButton;

    @FXML
    private Label messageLabel;

    @FXML
    private void handleLogin(ActionEvent event) {

        String email = emailField.getText().trim();
        String password = passwordField.getText();

        // Basic validation
        if (email.isEmpty() || password.isEmpty()) {
            messageLabel.setText("Please enter your email and password.");
            return;
        }

        try {
            authManager.login(email, password);

            // Login successful
            messageLabel.setText("Login successful!");

            // We'll navigate to the Todo page here next.
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("/todoapp/ui/todo-view.fxml")
            );

            Parent root = loader.load();

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

            stage.setScene(new Scene(root, 800, 600));
            stage.setTitle("Todo App - Todo");

        } catch (Exception e) {
            messageLabel.setText("Invalid email or password.");
            System.out.println("Login failed: " + e.getMessage());
        }
    }
    @FXML
    private void handleRegister(ActionEvent event) throws IOException {

        FXMLLoader loader = new FXMLLoader(
                getClass().getResource("/todoapp/ui/register-view.fxml")
        );

        Parent root = loader.load();

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

        stage.setScene(new Scene(root, 800, 600));
        stage.setTitle("Todo App - Register");
    }
}
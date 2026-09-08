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

public class RegisterController {
    private AuthManager authManager;

    public void setAuthManager(AuthManager authManager) {
        this.authManager = authManager;
    }

    @FXML
    private TextField emailField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private PasswordField repeatPasswordField;

    @FXML
    private Button registerButton;

    @FXML
    private Label messageLabel;

    @FXML
    private void handleRegister(ActionEvent event) {

        String email = emailField.getText().trim();
        String password = passwordField.getText();
        String repeatPassword = repeatPasswordField.getText();

        if (email.isEmpty() || password.isEmpty() || repeatPassword.isEmpty()) {
            messageLabel.setText("Please fill in all fields.");
            return;
        }

        if (!password.equals(repeatPassword)) {
            messageLabel.setText("Passwords do not match.");
            return;
        }

        try {
            authManager.register(email, password);


        } catch (Exception e) {
            messageLabel.setText("Registration failed.");
            System.out.println("Registration failed: " + e.getMessage());
        }
    }

    @FXML
    private void handleLogin(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(
                getClass().getResource("/todoapp/ui/login-view.fxml")
        );

        Parent root = loader.load();

        LoginController controller = loader.getController();
        controller.setAuthManager(authManager);

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

        stage.setScene(new Scene(root, 800, 600));
        stage.setTitle("Todo App - Login");
    }
}
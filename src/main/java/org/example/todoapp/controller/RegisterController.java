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

import java.io.IOException;

public class RegisterController {

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
    private void handleRegister() {

        String email = emailField.getText();
        String password = passwordField.getText();
        String repeatPassword = repeatPasswordField.getText();

        if (!password.equals(repeatPassword)) {
            messageLabel.setText("Passwords do not match.");
            return;
        }

        System.out.println("Email: " + email);
        System.out.println("Password: " + password);
    }

    @FXML
    private void handleLogin(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(
                getClass().getResource("/todoapp/ui/login-view.fxml")
        );

        Parent root = loader.load();

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

        stage.setScene(new Scene(root, 800, 600));
        stage.setTitle("Todo App - Login");
    }
}
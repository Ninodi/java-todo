package org.example.todoapp.controller;

import javafx.fxml.FXML;
import org.example.todoapp.navigation.AppPage;
import org.example.todoapp.navigation.AppRouter;

public class AddTodoController {

    private AppRouter router;

    public void setRouter(AppRouter router) {
        this.router = router;
    }

    @FXML
    public void handleBack() {
        router.navigateTo(AppPage.DASHBOARD);
    }

    @FXML
    public void handleSave() {
        // save todo
    }
}
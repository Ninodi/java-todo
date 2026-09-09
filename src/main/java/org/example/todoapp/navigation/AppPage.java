package org.example.todoapp.navigation;

public enum AppPage {

    LOGIN(
            "/todoapp/ui/LoginView.fxml",
            "Login"
    ),

    REGISTER(
            "/todoapp/ui/RegisterView.fxml",
            "Register"
    ),

    DASHBOARD(
            "/todoapp/ui/DashboardView.fxml",
            "Dashboard"
    ),

    ADD_TODO(
            "/todoapp/ui/AddTodoView.fxml",
            "Add Todo"
    );

    private final String viewPath;
    private final String title;

    AppPage(String viewPath, String title) {
        this.viewPath = viewPath;
        this.title = title;
    }

    public String getViewPath() {
        return viewPath;
    }

    public String getTitle() {
        return title;
    }
}
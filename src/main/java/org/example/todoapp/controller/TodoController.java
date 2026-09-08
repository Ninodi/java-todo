package org.example.todoapp.controller;

import org.example.todoapp.auth.AuthManager;

public class TodoController {
    private AuthManager authManager;

    public void setAuthManager(AuthManager authManager) {
        this.authManager = authManager;
    }
}

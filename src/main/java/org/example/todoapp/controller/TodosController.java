package org.example.todoapp.controller;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import org.example.todoapp.database.TodoService;
import org.example.todoapp.model.Todo;

import java.util.List;

public class TodosController {

    @FXML
    private ListView<Todo> todoList;

    private TodoService todoService;

    public void setTodoService(TodoService todoService) {

        this.todoService = todoService;

        setupTodoList();

        loadTodos();
    }

    private void setupTodoList() {

        todoList.setCellFactory(list -> new ListCell<>() {

            @Override
            protected void updateItem(
                    Todo todo,
                    boolean empty
            ) {

                super.updateItem(todo, empty);

                if (empty || todo == null) {
                    setText(null);
                } else {
                    setText(todo.getTitle());
                }
            }
        });
    }

    private void loadTodos() {

        try {

            List<Todo> todos =
                    todoService.getTodos();

            todoList.setItems(
                    FXCollections.observableArrayList(todos)
            );

        } catch (Exception e) {

            e.printStackTrace();
        }
    }
}
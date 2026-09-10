package org.example.todoapp.model;

import java.time.LocalDate;

public class Category {
    private String id;
    private String title;
    private String color;

    public Category() {
    }

    public Category(
            String id,
            String title,
            String color
    ) {
        this.id = id;
        this.title = title;
        this.color = color;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }
}

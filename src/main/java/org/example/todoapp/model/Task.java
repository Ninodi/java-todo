package org.example.todoapp.model;
import java.time.LocalDate;


public class Task {

    private String title;
    private Boolean completed;
    private String category;
    private LocalDate dueDate;

    public Task() {

    }

    public Task(String title, String category, LocalDate dueDate) {
        this.title = title;
        this.category = category;
        this.dueDate = dueDate;
        this.completed = false;
    }

    //GETTERs
    public String getTitle() {
        return title;
    }

    public String getCategory() {
        return category;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    //SETTERS
    public void setTitle(String title) {
        this.title = title;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }

    public void setCompleted(Boolean completed) {
        this.completed = completed;
    }

    //OTHER
    public Boolean isCompleted() {
        return completed;
    }


    public String normalizeStatus(Boolean completed) {
        if (completed) return "✓";
        return "";
    }


    @Override
    public String toString() {
        return normalizeStatus(completed) + ' ' +  title +  " [" + category + "] — Due: " + dueDate;
    }
}
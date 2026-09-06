package org.example.todoapp.persistence;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.example.todoapp.model.Task;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class TaskStorage {

    private static final String FILE_NAME = "tasks.json";

    private final ObjectMapper objectMapper;

    public TaskStorage() {
        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
    }

    public void saveTasks(List<Task> tasks) {

        try {
            objectMapper.writerWithDefaultPrettyPrinter()
                    .writeValue(new File(FILE_NAME), tasks);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<Task> loadTasks() {

        File file = new File(FILE_NAME);

        if (!file.exists()) {
            return new ArrayList<>();
        }

        try {
            return objectMapper.readValue(
                    file,
                    new TypeReference<List<Task>>() {}
            );

        } catch (IOException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }
}
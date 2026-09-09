package org.example.todoapp.database;

import com.fasterxml.jackson.databind.JsonNode;
import org.example.todoapp.auth.AuthSession;
import org.example.todoapp.model.Todo;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TodoService {

    private final FirestoreClient firestoreClient;
    private final AuthSession session;

    public TodoService(AuthSession session) {
        this.firestoreClient =
                new FirestoreClient(session);

        this.session = session;
    }

    private String getTodosPath() {
        return "/users/"
                + session.getUid()
                + "/todos";
    }

    public void createTodo(String title, String description, LocalDate date) throws Exception {
        String formattedDate = "";

        if (date != null) {
            // Convert LocalDate to standard ISO-8601 Timestamp format at Midnight UTC
            // This yields: "2026-09-09T00:00:00.000Z"
            formattedDate = date.atStartOfDay()
                    .format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'"));
        }

        String body = String.format(
                "{\n" +
                        "  \"fields\": {\n" +
                        "    \"title\": { \"stringValue\": \"%s\" },\n" +
                        "    \"description\": { \"stringValue\": \"%s\" },\n" +
                        "    \"dueDate\": { \"timestampValue\": \"%s\" },\n" +
                        "    \"completed\": { \"booleanValue\": false }\n" +
                        "  }\n" +
                        "}",
                title, description, formattedDate
        );

        JsonNode response = firestoreClient.post(getTodosPath(), body);
    }

    public void testCreateTodo()
            throws Exception {

        String body = """
            {
              "fields": {
                "title": {
                  "stringValue": "Test POST Todo"
                },
                "description": {
                  "stringValue": "Created using FirestoreClient"
                },
                "completed": {
                  "booleanValue": false
                }
              }
            }
            """;

        JsonNode response =
                firestoreClient.post(
                        getTodosPath(),
                        body
                );

        System.out.println("Created todo:");
        System.out.println(response);
    }


    public List<Todo> getTodos()
            throws Exception {

        JsonNode response =
                firestoreClient.get(
                        getTodosPath()
                );

        List<Todo> todos =
                new ArrayList<>();

        if (response == null) {
            return todos;
        }

        JsonNode documents =
                response.get("documents");

        if (documents == null) {
            return todos;
        }

        for (JsonNode document : documents) {

            String name =
                    document.get("name").asText();

            String id =
                    name.substring(
                            name.lastIndexOf("/") + 1
                    );

            JsonNode fields =
                    document.get("fields");

            String title =
                    fields.get("title")
                            .get("stringValue")
                            .asText();

            String description =
                    fields.get("description")
                            .get("stringValue")
                            .asText();

            boolean completed =
                    fields.get("completed")
                            .get("booleanValue")
                            .asBoolean();

            Todo todo = new Todo(
                    id,
                    title,
                    description,
                    completed,
                    null
            );

            todos.add(todo);
        }

        return todos;
    }
}
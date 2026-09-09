package org.example.todoapp.database;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.todoapp.auth.AuthSession;
import org.example.todoapp.config.FirebaseConfig;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class FirestoreClient {

    private static final String BASE_URL =
            "https://firestore.googleapis.com/v1/projects/";

    private final HttpClient httpClient;
    private final ObjectMapper objectMapper;
    private final AuthSession session;

    public FirestoreClient(AuthSession session) {
        this.httpClient = HttpClient.newHttpClient();
        this.objectMapper = new ObjectMapper();
        this.session = session;
    }

    private String getDocumentsUrl() {
        return BASE_URL
                + FirebaseConfig.getProjectId()
                + "/databases/(default)/documents";
    }

    public JsonNode get(String path)
            throws IOException, InterruptedException {

        return sendRequest(
                "GET",
                path,
                null
        );
    }

    public JsonNode post(
            String path,
            String body
    ) throws IOException, InterruptedException {

        return sendRequest(
                "POST",
                path,
                body
        );
    }

    public JsonNode patch(
            String path,
            String body
    ) throws IOException, InterruptedException {

        return sendRequest(
                "PATCH",
                path,
                body
        );
    }

    public void delete(String path)
            throws IOException, InterruptedException {

        sendRequest(
                "DELETE",
                path,
                null
        );
    }

    private JsonNode sendRequest(
            String method,
            String path,
            String body
    ) throws IOException, InterruptedException {

        if (!session.isLoggedIn()) {
            throw new IllegalStateException(
                    "User must be logged in."
            );
        }

        String url = getDocumentsUrl() + path;

        HttpRequest.Builder builder =
                HttpRequest.newBuilder()
                        .uri(URI.create(url))
                        .header(
                                "Authorization",
                                "Bearer " + session.getIdToken()
                        )
                        .header(
                                "Content-Type",
                                "application/json"
                        );

        switch (method) {
            case "GET" ->
                    builder.GET();

            case "POST" ->
                    builder.POST(
                            HttpRequest.BodyPublishers
                                    .ofString(body)
                    );

            case "PATCH" ->
                    builder.method(
                            "PATCH",
                            HttpRequest.BodyPublishers
                                    .ofString(body)
                    );

            case "DELETE" ->
                    builder.DELETE();

            default ->
                    throw new IllegalArgumentException(
                            "Unsupported HTTP method: "
                                    + method
                    );
        }

        HttpResponse<String> response =
                httpClient.send(
                        builder.build(),
                        HttpResponse.BodyHandlers
                                .ofString()
                );

        if (response.statusCode() < 200
                || response.statusCode() >= 300) {

            throw new IOException(
                    "Firestore request failed: "
                            + response.statusCode()
                            + " - "
                            + response.body()
            );
        }

        if (response.body().isBlank()) {
            return null;
        }

        return objectMapper.readTree(
                response.body()
        );
    }

    public ObjectMapper getObjectMapper() {
        return objectMapper;
    }
}
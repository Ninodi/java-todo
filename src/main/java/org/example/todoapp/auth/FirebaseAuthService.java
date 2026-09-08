package org.example.todoapp.auth;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.todoapp.config.FirebaseConfig;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Map;

public class FirebaseAuthService {

    private static final String BASE_URL =
            "https://identitytoolkit.googleapis.com/v1/accounts";

    private final HttpClient httpClient;
    private final ObjectMapper objectMapper;

    public FirebaseAuthService() {
        this.httpClient = HttpClient.newHttpClient();
        this.objectMapper = new ObjectMapper();
    }

    public AuthResult register(String email, String password)
            throws IOException, InterruptedException, FirebaseAuthException {

        String url = BASE_URL
                + ":signUp?key="
                + FirebaseConfig.getApiKey();

        Map<String, Object> requestBody = Map.of(
                "email", email,
                "password", password,
                "returnSecureToken", true
        );



        String jsonBody = objectMapper.writeValueAsString(requestBody);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(jsonBody))
                .build();

        HttpResponse<String> response = httpClient.send(
                request,
                HttpResponse.BodyHandlers.ofString()
        );

        JsonNode json = objectMapper.readTree(response.body());

        if (response.statusCode() != 200) {
            handleError(json);
        }

        return parseAuthResult(json);
    }

    public AuthResult login(String email, String password)
            throws IOException, InterruptedException, FirebaseAuthException {

        String url = BASE_URL
                + ":signInWithPassword?key="
                + FirebaseConfig.getApiKey();

        Map<String, Object> requestBody = Map.of(
                "email", email,
                "password", password,
                "returnSecureToken", true
        );

        String jsonBody = objectMapper.writeValueAsString(requestBody);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(jsonBody))
                .build();

        HttpResponse<String> response = httpClient.send(
                request,
                HttpResponse.BodyHandlers.ofString()
        );

        JsonNode json = objectMapper.readTree(response.body());

        if (response.statusCode() != 200) {
            handleError(json);
        }

        return parseAuthResult(json);
    }

    public AuthResult refreshIdToken(String refreshToken)
            throws IOException, InterruptedException, FirebaseAuthException {

        String url =
                "https://securetoken.googleapis.com/v1/token?key="
                        + FirebaseConfig.getApiKey();

        String body =
                "grant_type=refresh_token"
                        + "&refresh_token="
                        + java.net.URLEncoder.encode(
                        refreshToken,
                        java.nio.charset.StandardCharsets.UTF_8
                );

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .header(
                        "Content-Type",
                        "application/x-www-form-urlencoded"
                )
                .POST(HttpRequest.BodyPublishers.ofString(body))
                .build();

        HttpResponse<String> response = httpClient.send(
                request,
                HttpResponse.BodyHandlers.ofString()
        );

        JsonNode json = objectMapper.readTree(response.body());

        if (response.statusCode() != 200) {
            handleError(json);
        }

        return new AuthResult(
                json.get("user_id").asText(),
                null,
                json.get("id_token").asText(),
                json.get("refresh_token").asText(),
                json.get("expires_in").asText()
        );
    }

    private void handleError(JsonNode json)
            throws FirebaseAuthException {

        JsonNode error = json.get("error");

        if (error == null) {
            throw new FirebaseAuthException(
                    "UNKNOWN_ERROR",
                    "An unknown authentication error occurred."
            );
        }

        String errorCode = error
                .get("message")
                .asText();

        throw new FirebaseAuthException(
                errorCode,
                getReadableErrorMessage(errorCode)
        );
    }

    private String getReadableErrorMessage(String errorCode) {

        return switch (errorCode) {

            case "EMAIL_EXISTS" ->
                    "An account with this email already exists.";

            case "INVALID_EMAIL" ->
                    "The email address is invalid.";

            case "OPERATION_NOT_ALLOWED" ->
                    "Email/password authentication is not enabled.";

            case "TOO_MANY_ATTEMPTS_TRY_LATER" ->
                    "Too many attempts. Please try again later.";

            case "WEAK_PASSWORD" ->
                    "The password is too weak.";

            case "INVALID_LOGIN_CREDENTIALS",
                 "INVALID_PASSWORD",
                 "USER_NOT_FOUND" ->
                    "Invalid email or password.";

            case "USER_DISABLED" ->
                    "This account has been disabled.";

            default ->
                    "Authentication failed.";
        };
    }

    private AuthResult parseAuthResult(JsonNode json) {

        return new AuthResult(
                json.get("localId").asText(),
                json.get("email").asText(),
                json.get("idToken").asText(),
                json.get("refreshToken").asText(),
                json.get("expiresIn").asText()
        );
    }
}
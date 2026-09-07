package org.example.todoapp.config;

import io.github.cdimascio.dotenv.Dotenv;

public class FirebaseConfig {

    private static final Dotenv dotenv = Dotenv.load();

    private FirebaseConfig() {
    }

    public static String getApiKey() {
        String apiKey = dotenv.get("FIREBASE_API_KEY");

        if (apiKey == null || apiKey.isBlank()) {
            throw new IllegalStateException(
                    "FIREBASE_API_KEY is missing from .env"
            );
        }

        return apiKey;
    }
}
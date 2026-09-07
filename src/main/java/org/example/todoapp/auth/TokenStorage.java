package org.example.todoapp.auth;

import java.io.*;
import java.nio.file.*;
import java.util.Properties;

public class TokenStorage {

    private static final Path FILE_PATH =
            Paths.get(System.getProperty("user.home"), ".todoapp", "auth.properties");

    public void saveRefreshToken(String refreshToken) throws IOException {

        Path parent = FILE_PATH.getParent();

        if (!Files.exists(parent)) {
            Files.createDirectories(parent);
        }

        Properties properties = new Properties();
        properties.setProperty("refreshToken", refreshToken);

        try (OutputStream outputStream = Files.newOutputStream(FILE_PATH)) {
            properties.store(outputStream, "Todo App Authentication");
        }
    }

    public String getRefreshToken() throws IOException {

        if (!Files.exists(FILE_PATH)) {
            return null;
        }

        Properties properties = new Properties();

        try (InputStream inputStream = Files.newInputStream(FILE_PATH)) {
            properties.load(inputStream);
        }

        return properties.getProperty("refreshToken");
    }

    public void clear() throws IOException {

        Files.deleteIfExists(FILE_PATH);
    }
}
package org.example.todoapp.auth;

public class AuthResult {

    private final String uid;
    private final String email;
    private final String idToken;
    private final String refreshToken;
    private final String expiresIn;

    public AuthResult(
            String uid,
            String email,
            String idToken,
            String refreshToken,
            String expiresIn
    ) {
        this.uid = uid;
        this.email = email;
        this.idToken = idToken;
        this.refreshToken = refreshToken;
        this.expiresIn = expiresIn;
    }

    public String getUid() {
        return uid;
    }

    public String getEmail() {
        return email;
    }

    public String getIdToken() {
        return idToken;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public String getExpiresIn() {
        return expiresIn;
    }
}
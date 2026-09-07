package org.example.todoapp.auth;

public class AuthSession {

    private String uid;
    private String email;
    private String idToken;
    private String refreshToken;
    private long expiresAt;

    private final TokenStorage tokenStorage;

    public AuthSession() {
        this.tokenStorage = new TokenStorage();

        try {
            this.refreshToken = tokenStorage.getRefreshToken();
        } catch (Exception e) {
            System.out.println("Could not load saved authentication: "
                    + e.getMessage());
        }
    }

    public boolean isLoggedIn() {
        return idToken != null && !idToken.isBlank();
    }

    public void start(AuthResult authResult) {

        this.uid = authResult.getUid();
        this.email = authResult.getEmail();
        this.idToken = authResult.getIdToken();
        this.refreshToken = authResult.getRefreshToken();

        long expiresInSeconds =
                Long.parseLong(authResult.getExpiresIn());

        this.expiresAt =
                System.currentTimeMillis()
                        + (expiresInSeconds * 1000);

        try {
            tokenStorage.saveRefreshToken(this.refreshToken);
        } catch (Exception e) {
            System.out.println("Could not save authentication: "
                    + e.getMessage());
        }
    }

    public void logout() {

        this.uid = null;
        this.email = null;
        this.idToken = null;
        this.refreshToken = null;
        this.expiresAt = 0;

        try {
            tokenStorage.clear();
        } catch (Exception e) {
            System.out.println("Could not clear saved authentication: "
                    + e.getMessage());
        }
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

    public long getExpiresAt() {
        return expiresAt;
    }
}
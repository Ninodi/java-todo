package org.example.todoapp.auth;

public class AuthManager {

    private final FirebaseAuthService authService;
    private final AuthSession session;

    public AuthManager() {
        this.authService = new FirebaseAuthService();
        this.session = new AuthSession();
        restoreSession();
    }

    public void register(String email, String password)
            throws Exception {

        AuthResult result =
                authService.register(email, password);

        session.start(result);
    }

    public void login(String email, String password)
            throws Exception {

        AuthResult result =
                authService.login(email, password);

        session.start(result);
    }

    public void logout() {
        session.logout();
    }

    public boolean isLoggedIn() {
        return session.isLoggedIn();
    }

    public AuthSession getSession() {
        return session;
    }

    private void restoreSession() {

        String refreshToken = session.getRefreshToken();

        if (refreshToken == null || refreshToken.isBlank()) {
            return;
        }

        try {

            AuthResult result =
                    authService.refreshIdToken(refreshToken);

            session.start(result);

            System.out.println("Authentication session restored.");

        } catch (Exception e) {

            System.out.println(
                    "Could not restore authentication session: "
                            + e.getMessage()
            );

            session.logout();
        }
    }
}
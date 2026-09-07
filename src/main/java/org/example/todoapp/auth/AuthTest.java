package org.example.todoapp.auth;

public class AuthTest {

    public static void main(String[] args) {

        FirebaseAuthService authService =
                new FirebaseAuthService();

        AuthSession session =
                new AuthSession();

        try {

            AuthResult result = authService.login(
                    "test@example.com",
                    "password123"
            );

            session.start(result);

            System.out.println("Login successful!");
            System.out.println("UID: " + session.getUid());
            System.out.println("Email: " + session.getEmail());
            System.out.println("Logged in: " + session.isLoggedIn());

        } catch (FirebaseAuthException e) {

            System.out.println(
                    "Firebase error: " + e.getMessage()
            );

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
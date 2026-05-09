package main.exception;

/** Thrown when a user tries to login with wrong credentials. */
public class InvalidCredentialsException extends Exception {
    public InvalidCredentialsException() {
        super("Invalid username or password. Please try again.");
    }
}

package main.model;

/**
 * Abstract base class representing a system user.
 * Demonstrates: Abstraction, Encapsulation, Inheritance
 */
public abstract class User {
    private String userId;
    private String username;
    private String password;
    private String email;
    private String role;

    public User(String userId, String username, String password, String email, String role) {
        this.userId = userId;
        this.username = username;
        this.password = password;
        this.email = email;
        this.role = role;
    }

    // Abstract method — each subclass defines its own menu
    public abstract void showMenu();

    // Getters and Setters
    public String getUserId()          { return userId; }
    public String getUsername()        { return username; }
    public String getPassword()        { return password; }
    public String getEmail()           { return email; }
    public String getRole()            { return role; }
    public void setPassword(String p)  { this.password = p; }
    public void setEmail(String e)     { this.email = e; }

    @Override
    public String toString() {
        return userId + "," + username + "," + password + "," + email + "," + role;
    }
}

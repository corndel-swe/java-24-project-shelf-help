package org.project.shelfhelp.models;

public class User {
    private int id;
    private String firstName;
    private String lastName;
    private String username;
    private String password;
    private String avatarUrl;

    public User(int id, String avatarUrl, String password, String username, String lastName, String firstName) {
        this.id = id;
        this.avatarUrl = avatarUrl;
        this.password = password;
        this.username = username;
        this.lastName = lastName;
        this.firstName = firstName;
    }

    // getters and setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
package com.example.numad22fa_group24.models;

public class User {
    private String uid;
    private String username;
    private String email;
    private String status;

    public User(String uid, String username, String email, String status) {
        this.uid = uid;
        this.username = username;
        this.email = email;
        this.status = status;
    }

    public User() {

    }

    public String getUid() {
        return uid;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public String getStatus() {
        return status;
    }

    @Override
    public String toString() {
        return "User{" +
                "uid='" + uid + '\'' +
                ", username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", status='" + status + '\'' +
                '}' + "\n";
    }
}


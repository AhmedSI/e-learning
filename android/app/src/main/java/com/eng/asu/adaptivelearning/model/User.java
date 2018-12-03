package com.eng.asu.adaptivelearning.model;

public class User {

    private int id;
    private String name;
    private String email;
    private UserType type;
    private String accessToken;

    public User() {
    }

    public User(int id, String name, String email, UserType type, String accessToken) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.type = type;
        this.accessToken = accessToken;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public UserType getType() {
        return type;
    }

    public void setType(UserType type) {
        this.type = type;
    }

    public String getToken() {
        return accessToken;
    }

    public void setToken(String token) {
        this.accessToken = token;
    }
}

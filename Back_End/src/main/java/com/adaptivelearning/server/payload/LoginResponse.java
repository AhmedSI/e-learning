package com.adaptivelearning.server.payload;

import com.adaptivelearning.server.Model.User;

public class LoginResponse {

    private User user;
    private int code;
    private String token;

    public LoginResponse(int code, String token, User user) {
        this.code = code;
        this.token = token;
        this.user = user;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}



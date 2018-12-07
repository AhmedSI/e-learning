package com.adaptivelearning.server.payload;

import com.adaptivelearning.server.Model.User;

import java.util.Optional;

public class LoginResponse {

    Optional<User> user;
    private int code;
    private String token;

    public LoginResponse(int code, String token, Optional<User> user) {
        this.code = code;
        this.token = token;
        this.user = user;
    }

    public Optional<User> getUser() {
        return user;
    }

    public void setUser(Optional<User> user) {
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



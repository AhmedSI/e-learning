package com.login.GP.payload;

import java.util.Optional;

import org.springframework.http.ResponseEntity;

import com.login.GP.Model.User;

public class LoginResponse {
	
	    private int code;
	    private String token;
	    Optional<User> user ;

	    public Optional<User> getUser() {
			return user;
		}

		public void setUser(Optional<User> user) {
			this.user = user;
		}

		public LoginResponse(int code,String token,Optional<User> user) {
	        this.code = code;
	        this.token =  token;
	        this.user=user;
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



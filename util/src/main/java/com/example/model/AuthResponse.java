package com.example.model;

public class AuthResponse {

    String token;
    boolean success;
    
    public AuthResponse() {
    }

    public AuthResponse(String token, boolean success) {
        this.success = success;
        this.token = token;
    }
    public String getToken() {
        return token;
    }
    public void setToken(String token) {
        this.token = token;
    }
    public boolean isSuccess() {
        return success;
    }
    public void setSuccess(boolean success) {
        this.success = success;
    }

    
}

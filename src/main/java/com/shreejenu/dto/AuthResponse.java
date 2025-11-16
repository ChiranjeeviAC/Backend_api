package com.shreejenu.dto;

// We have removed 'import lombok.Data;' and the @Data annotation
public class AuthResponse {
    private String token;

    // Manual constructor
    public AuthResponse(String token) {
        this.token = token;
    }

    // Getters and Setters
    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
package com.api.stackoverflow_backend.dtos;

public class AuthenticationResponse {

    private String jwtToken;

    // Construtor
    public AuthenticationResponse(String jwtToken) {
        this.jwtToken = jwtToken;
    }

    // Método Getters e Setters
    public String getJwtToken() {
        return jwtToken;
    }

    public void setJwtToken(String jwtToken) {
        this.jwtToken = jwtToken;
    }

}

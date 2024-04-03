package com.airbnb.dto;

public class TokenResponse {
    private String Type="Bearer";
    private String token;

    public String getType() {
        return Type;
    }

    public void setType(String type) {
        Type = type;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}

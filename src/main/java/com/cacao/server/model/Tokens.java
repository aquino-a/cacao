package com.cacao.server.model;

/**
 * Represents the relevant tokens recieved by google oauth
 */
public class Tokens{
    private String accessToken, idToken;

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getIdToken() {
        return idToken;
    }

    public void setIdToken(String idToken) {
        this.idToken = idToken;
    }
}

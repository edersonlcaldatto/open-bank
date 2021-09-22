package com.ciss.banco.model.auth;

import java.io.Serializable;

public class JwtResponse implements Serializable {

    private final String token;
    private final String error;

    public JwtResponse(String token, String error) {
        this.token = token;
        this.error = error;
    }

    public String getToken() {
        return this.token;
    }

    public String getError() {
        return error;
    }
}

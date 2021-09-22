package com.ciss.banco.model.auth;

import java.io.Serializable;

public class JwtRequest implements Serializable {

    private String email;
    private String senha;

    public JwtRequest() {
    }

    public JwtRequest(String email, String senha) {
        this.setSenha(email);
        this.setSenha(senha);
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }
}

package com.rafalopez.inmobiliaria.entity;

public class User {
    private String email;
    private String password;
    private String token= null;
    private Propietario propietario=null;

    public User(String email, String password) {
        this.email=email;
        this.password=password;
    }

    public User(String token, Propietario propietario) {
        this.token = token;
        this.propietario = propietario;
    }

    public User(String email, String password, String token) {
        this.email = email;
        this.password = password;
        this.token = token;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Propietario getPropietario() {
        return propietario;
    }

    public void setPropietario(Propietario propietario) {
        this.propietario = propietario;
    }

    @Override
    public String toString() {
        return "User{" +
                "email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", token='" + token + '\'' +
                ", propietario=" + propietario +
                '}';
    }
}

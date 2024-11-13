package com.rafalopez.inmobiliaria.entity;

public class User {
    private String email;
    private String password;
    private String token = null;
    private int otp= -1 ;
    public User() {}

    public User(String email, String password) {
        this.email=email;
       this.password=password;
    }

    public User(String token, int otp){
        this.token=token;
        this.otp=otp;
    }

    public User(String token) {
        this.token = token;
    }
    public User(String email, String password, String token) {
        this.email = email;
        this.password = password;
        this.token = token;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {this.email = email;}

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

    @Override
    public String toString() {
        return "User{\n" +
                "email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", token='" + token +
                "\n }";
    }
}

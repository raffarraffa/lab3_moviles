package com.rafalopez.tp2_object_input_stream.model;

import java.io.Serializable;

public class Usuario implements Serializable {
    private String nombre;
    private String apellido;
    private String email;
    private int dni=0;
    private String pass;
    public Usuario() {}
    public Usuario(String nombre, String apellido, String email, int dni, String pass) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.email = email;
        this.dni = dni;
        this.pass = pass;
    }

    public int getDni() {
        return dni;
    }

    public void setDni(int dni) {
        this.dni = dni;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public int getTelefono() {
        return dni;
    }
    public void setTelefono(int dni) {
        this.dni = dni;
    }
    public String getPass() {
        return pass;
    }
    public void setPass(String pass) {
        this.pass = pass;
    }
    public boolean isValid(){
        if(nombre.isEmpty() || apellido.isEmpty() || email.isEmpty() || dni<=0 || pass.isEmpty() ) return false;
        return true;
    }
    @Override
    public String toString() {
        return "Usuario{" +
                "nombre='" + nombre + '\'' +
                ", apellido='" + apellido + '\'' +
                ", email='" + email + '\'' +
                ", dni=" + dni +
                ", pass='" + pass + '\'' +
                ", dni='" + dni + '\'' +
                '}';
    }
}

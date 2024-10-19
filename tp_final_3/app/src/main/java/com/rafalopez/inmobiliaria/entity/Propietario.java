package com.rafalopez.inmobiliaria.entity;

public class Propietario {


        private int id;
        private String nombre;
        private String apellido;
        private String dni;
        private String email;
        private String telefono;
        private String password;
        private String avatar;
        private boolean borrado;
 // Relaciones
//        private Set<Inmueble> inmuebles = new HashSet<>();
//        private Set<Contrato> contratos = new HashSet<>();

    public Propietario(int id, String nombre, String apellido, String dni, String email, String telefono, String password, String avatar, boolean borrado) {
        this.id = id;
        this.nombre = nombre;
        this.apellido = apellido;
        this.dni = dni;
        this.email = email;
        this.telefono = telefono;
        this.password = password;
        this.avatar = avatar;
        this.borrado = borrado;
    }

    public Propietario(String nombre, String apellido, String dni, String email, String telefono, String avatar) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.dni = dni;
        this.email = email;
        this.telefono = telefono;
        this.avatar = avatar;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public boolean isBorrado() {
        return borrado;
    }

    public void setBorrado(boolean borrado) {
        this.borrado = borrado;
    }

    @Override
    public String toString() {
        return "Propietario{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", apellido='" + apellido + '\'' +
                ", dni='" + dni + '\'' +
                ", email='" + email + '\'' +
                ", telefono='" + telefono + '\'' +
                ", password='" + password + '\'' +
                ", avatar='" + avatar + '\'' +
                ", borrado=" + borrado +
                '}';
    }

}

package com.rafalopez.inmobiliaria.entity;


import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

public class Contrato {

    private int id;
    private int idInquilino;
    private int idInmueble;
    private LocalDateTime fechaInicio;
    private LocalDateTime fechaFin;
    private LocalDateTime fechaEfectiva;
    private Double monto;
    private Boolean borrado;
    private LocalDateTime creadoFecha;
    private int creadoUsuario;
    private LocalDateTime canceladoFecha;
    private int canceladoUsuario;
    private Integer editadoUsuario;
    private LocalDateTime editadoFecha;
    private int propietarioId;


 //   private Inquilino inquilino;
    private Inmueble inmueble;
   // private List<Pago> pagos;
    public Contrato(){}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdInquilino() {
        return idInquilino;
    }

    public void setIdInquilino(int idInquilino) {
        this.idInquilino = idInquilino;
    }

    public int getIdInmueble() {
        return idInmueble;
    }

    public void setIdInmueble(int idInmueble) {
        this.idInmueble = idInmueble;
    }

    public LocalDateTime getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(LocalDateTime fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public LocalDateTime getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(LocalDateTime fechaFin) {
        this.fechaFin = fechaFin;
    }

    public LocalDateTime getFechaEfectiva() {
        return fechaEfectiva;
    }

    public void setFechaEfectiva(LocalDateTime fechaEfectiva) {
        this.fechaEfectiva = fechaEfectiva;
    }

    public Double getMonto() {
        return monto;
    }

    public void setMonto(Double monto) {
        this.monto = monto;
    }

    public Boolean getBorrado() {
        return borrado;
    }

    public void setBorrado(Boolean borrado) {
        this.borrado = borrado;
    }

    public LocalDateTime getCreadoFecha() {
        return creadoFecha;
    }

    public void setCreadoFecha(LocalDateTime creadoFecha) {
        this.creadoFecha = creadoFecha;
    }

    public int getCreadoUsuario() {
        return creadoUsuario;
    }

    public void setCreadoUsuario(int creadoUsuario) {
        this.creadoUsuario = creadoUsuario;
    }

    public LocalDateTime getCanceladoFecha() {
        return canceladoFecha;
    }

    public void setCanceladoFecha(LocalDateTime canceladoFecha) {
        this.canceladoFecha = canceladoFecha;
    }

    public int getCanceladoUsuario() {
        return canceladoUsuario;
    }

    public void setCanceladoUsuario(int canceladoUsuario) {
        this.canceladoUsuario = canceladoUsuario;
    }

    public Integer getEditadoUsuario() {
        return editadoUsuario;
    }

    public void setEditadoUsuario(Integer editadoUsuario) {
        this.editadoUsuario = editadoUsuario;
    }

    public LocalDateTime getEditadoFecha() {
        return editadoFecha;
    }

    public void setEditadoFecha(LocalDateTime editadoFecha) {
        this.editadoFecha = editadoFecha;
    }

    public int getPropietarioId() {
        return propietarioId;
    }

    public void setPropietarioId(int propietarioId) {
        this.propietarioId = propietarioId;
    }

    public Inmueble getInmueble() {
        return inmueble;
    }

    public void setInmueble(Inmueble inmueble) {
        this.inmueble = inmueble;
    }

    @Override
    public String toString() {
        return "Contrato{" +
                "id=" + id +
                ", idInquilino=" + idInquilino +
                ", idInmueble=" + idInmueble +
                ", fechaInicio=" + fechaInicio +
                ", fechaFin=" + fechaFin +
                ", fechaEfectiva=" + fechaEfectiva +
                ", monto=" + monto +
                ", borrado=" + borrado +
                ", creadoFecha=" + creadoFecha +
                ", creadoUsuario=" + creadoUsuario +
                ", canceladoFecha=" + canceladoFecha +
                ", canceladoUsuario=" + canceladoUsuario +
                ", editadoUsuario=" + editadoUsuario +
                ", editadoFecha=" + editadoFecha +
                ", propietarioId=" + propietarioId +
                ", inmueble=" + inmueble +
                '}';
    }
}
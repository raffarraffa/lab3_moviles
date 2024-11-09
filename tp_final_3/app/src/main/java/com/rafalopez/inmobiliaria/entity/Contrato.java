package com.rafalopez.inmobiliaria.entity;


import java.util.Date;
import java.util.List;

public class Contrato {

    private int id;
    private int idInquilino;
    private int idInmueble;
    private Date fechaInicio;
    private Date fechaFin;
    private Date fechaEfectiva;
    private Double monto;
    private Boolean borrado;
    private Date creadoFecha;
    private int creadoUsuario;
    private Date canceladoFecha;
    private int canceladoUsuario;
    private Integer editadoUsuario;
    private Date editadoFecha;
    private int propietarioId;


 //   private Inquilino inquilino;
    private Inmueble inmueble;
   // private List<Pago> pagos;
    public Contrato(){}

    public Contrato(int id, int idInquilino, int idInmueble, Date fechaInicio, Date fechaFin, Date fechaEfectiva, Double monto, Boolean borrado, Date creadoFecha, int creadoUsuario, Date canceladoFecha, int canceladoUsuario, Integer editadoUsuario, Date editadoFecha, int propietarioId, Inmueble inmueble) {
        this.id = id;
        this.idInquilino = idInquilino;
        this.idInmueble = idInmueble;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.fechaEfectiva = fechaEfectiva;
        this.monto = monto;
        this.borrado = borrado;
        this.creadoFecha = creadoFecha;
        this.creadoUsuario = creadoUsuario;
        this.canceladoFecha = canceladoFecha;
        this.canceladoUsuario = canceladoUsuario;
        this.editadoUsuario = editadoUsuario;
        this.editadoFecha = editadoFecha;
        this.propietarioId = propietarioId;
        this.inmueble = inmueble;
    }

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

    public Date getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(Date fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public Date getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(Date fechaFin) {
        this.fechaFin = fechaFin;
    }

    public Date getFechaEfectiva() {
        return fechaEfectiva;
    }

    public void setFechaEfectiva(Date fechaEfectiva) {
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

    public Date getCreadoFecha() {
        return creadoFecha;
    }

    public void setCreadoFecha(Date creadoFecha) {
        this.creadoFecha = creadoFecha;
    }

    public int getCreadoUsuario() {
        return creadoUsuario;
    }

    public void setCreadoUsuario(int creadoUsuario) {
        this.creadoUsuario = creadoUsuario;
    }

    public Date getCanceladoFecha() {
        return canceladoFecha;
    }

    public void setCanceladoFecha(Date canceladoFecha) {
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

    public Date getEditadoFecha() {
        return editadoFecha;
    }

    public void setEditadoFecha(Date editadoFecha) {
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
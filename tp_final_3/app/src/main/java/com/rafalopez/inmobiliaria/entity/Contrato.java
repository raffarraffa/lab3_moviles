package com.rafalopez.inmobiliaria.entity;



import java.util.Date;
import java.util.List;

public class Contrato {

    private int id;
    private int idInquilino;
    private int idInmueble;
    private String fechaInicio;
    private String fechaFin;
    private String fechaEfectiva;
    private Double monto;
    private Boolean borrado;
    private String creadoFecha;
    private int creadoUsuario;
    private String canceladoFecha;
    private int canceladoUsuario;
    private Integer editadoUsuario;
    private String editadoFecha;
    private int propietarioId;


 //   private Inquilino inquilino;
    private Inmueble inmueble;

    private List<Pago> pagos;
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

    public String getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(String fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public String getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(String fechaFin) {
        this.fechaFin = fechaFin;
    }

    public String getFechaEfectiva() {
        return fechaEfectiva;
    }

    public void setFechaEfectiva(String fechaEfectiva) {
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

    public String getCreadoFecha() {
        return creadoFecha;
    }

    public void setCreadoFecha(String creadoFecha) {
        this.creadoFecha = creadoFecha;
    }

    public int getCreadoUsuario() {
        return creadoUsuario;
    }

    public void setCreadoUsuario(int creadoUsuario) {
        this.creadoUsuario = creadoUsuario;
    }

    public String getCanceladoFecha() {
        return canceladoFecha;
    }

    public void setCanceladoFecha(String canceladoFecha) {
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

    public String getEditadoFecha() {
        return editadoFecha;
    }

    public void setEditadoFecha(String editadoFecha) {
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

    public List<Pago> getPagos() {
        return pagos;
    }

    public void setPagos(List<Pago> pagos) {
        this.pagos = pagos;
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
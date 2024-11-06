package com.rafalopez.inmobiliaria.entity;
import java.io.Serializable;
import java.math.BigDecimal;

public class Inmueble implements Serializable {
    private int id;
    private String direccion;
    private String uso;
    private int idTipo;
    private byte ambientes;
    private String coordenadas;
    private BigDecimal precio;
    private int propietarioId;
    private String estado;
    private int idCiudad;
    private int idZona;
    private boolean borrado;
    private String descripcion;
    private String urlImg;
    private String ciudad;
    private  String tipo;
// Relaciones
 //   private Set<Contrato> contratos = new HashSet<>();

// Constructores
    public Inmueble() { }
    public Inmueble(int id, String direccion, String uso, int idTipo, byte ambientes,
                    String coordenadas, BigDecimal precio, int propietarioId,
                    String estado, int idCiudad, int idZona, boolean borrado,
                    String descripcion, String urlImg) {
        this.id = id;
        this.direccion = direccion;
        this.uso = uso;
        this.idTipo = idTipo;
        this.ambientes = ambientes;
        this.coordenadas = coordenadas;
        this.precio = precio;
        this.propietarioId = propietarioId;
        this.estado = estado;
        this.idCiudad = idCiudad;
        this.idZona = idZona;
        this.borrado = borrado;
        this.descripcion = descripcion;
        this.urlImg = urlImg;
    }

    public Inmueble(String direccion, String uso, int idTipo, byte ambientes,
                    String coordenadas, BigDecimal precio, int propietarioId,
                    String estado, int idCiudad, int idZona, boolean borrado,
                    String descripcion, String urlImg) {
        this.direccion = direccion;
        this.uso = uso;
        this.idTipo = idTipo;
        this.ambientes = ambientes;
        this.coordenadas = coordenadas;
        this.precio = precio;
        this.propietarioId = propietarioId;
        this.estado = estado;
        this.idCiudad = idCiudad;
        this.idZona = idZona;
        this.borrado = borrado;
        this.descripcion = descripcion;
        this.urlImg = urlImg;
    }

    // Getters y Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getUso() {
        return uso;
    }

    public void setUso(String uso) {
        this.uso = uso;
    }

    public int getIdTipo() {
        return idTipo;
    }

    public void setIdTipo(int idTipo) {
        this.idTipo = idTipo;
    }

    public byte getAmbientes() {
        return ambientes;
    }

    public void setAmbientes(byte ambientes) {
        this.ambientes = ambientes;
    }

    public String getCoordenadas() {
        return coordenadas;
    }

    public void setCoordenadas(String coordenadas) {
        this.coordenadas = coordenadas;
    }

    public BigDecimal getPrecio() {
        return precio;
    }

    public void setPrecio(String precio) {
        if (precio != null && !precio.isEmpty()) {
            try {
                this.precio = new BigDecimal(precio);
            } catch (NumberFormatException e) {
                this.precio = BigDecimal.ZERO;
            }
        } else {
            this.precio = BigDecimal.ZERO;
        }
    }

    public int getPropietarioId() {
        return propietarioId;
    }

    public void setPropietarioId(int propietarioId) {
        this.propietarioId = propietarioId;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public int getIdCiudad() {
        return idCiudad;
    }

    public void setIdCiudad(int idCiudad) {
        this.idCiudad = idCiudad;
    }

    public int getIdZona() {
        return idZona;
    }

    public void setIdZona(int idZona) {
        this.idZona = idZona;
    }

    public boolean isBorrado() {
        return borrado;
    }

    public void setBorrado(boolean borrado) {
        this.borrado = borrado;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getUrlImg() {
        return urlImg;
    }

    public void setUrlImg(String urlImg) {
        this.urlImg = urlImg;
    }

    public String getCiudad() {
        return ciudad;
    }

    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    @Override
    public String toString() {
        return "Inmueble{" +
                "id=" + id +
                ", direccion='" + direccion + '\'' +
                ", uso='" + uso + '\'' +
                ", idTipo=" + idTipo +
                ", ambientes=" + ambientes +
                ", coordenadas='" + coordenadas + '\'' +
                ", precio=" + precio +
                ", propietarioId=" + propietarioId +
                ", estado='" + estado + '\'' +
                ", idCiudad=" + idCiudad +
                ", idZona=" + idZona +
                ", borrado=" + borrado +
                ", descripcion='" + descripcion + '\'' +
                ", urlImg='" + urlImg + '\'' +
                ", ciudad='" + ciudad + '\'' +
                ", tipo='" + tipo + '\'' +
                '}';
    }

}

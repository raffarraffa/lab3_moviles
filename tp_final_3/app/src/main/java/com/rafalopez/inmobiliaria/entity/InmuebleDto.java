package com.rafalopez.inmobiliaria.entity;
import java.io.Serializable;
import java.math.BigDecimal;

public class InmuebleDto implements Serializable {
    private String direccion;
    private String uso;
    private byte ambientes;
    private String coordenadas;
    private BigDecimal precio;
  //  private String estado;
    private String descripcion;
    private String ciudad;
    private  String tipo;
// Relaciones
 //   private Set<Contrato> contratos = new HashSet<>();

// Constructores
    public InmuebleDto() { }

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





    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
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
        return "InmuebleDto{" +
                "direccion='" + direccion + '\'' +
                ", uso='" + uso + '\'' +
                ", ambientes=" + ambientes +
                ", coordenadas='" + coordenadas + '\'' +
                ", precio=" + precio +
                ", descripcion='" + descripcion + '\'' +
                ", ciudad='" + ciudad + '\'' +
                ", tipo='" + tipo + '\'' +
                '}';
    }
}

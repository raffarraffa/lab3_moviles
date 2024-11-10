package com.rafalopez.inmobiliaria.entity;

import com.rafalopez.inmobiliaria.utils.Utils;

import java.io.Serializable;
import java.util.Date;

public class Pago implements Serializable {


        private int id;
        private double importe;
        private String fechaPago;
        private String estado;
        private String fechaPagoStr;

    public Pago(int id) { }

    public Pago(int id, double importe, String fechaPago, String estado) {
        this.id = id;
        this.importe = importe;
        this.fechaPago = fechaPago;
        this.estado = estado;
        this.fechaPagoStr = Utils.stringToDate(fechaPago);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getImporte() {
        return importe;
    }

    public void setImporte(double importe) {
        this.importe = importe;
    }

    public String getFechaPago() {
        return fechaPago;
    }

    public void setFechaPago(String fechaPago) {
        this.fechaPago = fechaPago;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
    private String convertirDate(String fechaPago){
        return Utils.stringToDate(fechaPago);
    }

    @Override
    public String toString() {
        return "Pago N°: " + id + ", "+
               " Fecha: " + convertirDate(fechaPago) + ", "+
               " Importe: $" + importe + "\n";
    }
}


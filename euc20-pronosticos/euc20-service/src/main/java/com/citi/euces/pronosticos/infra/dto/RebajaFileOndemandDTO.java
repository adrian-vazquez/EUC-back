package com.citi.euces.pronosticos.infra.dto;

import java.io.Serializable;

public class RebajaFileOndemandDTO implements Serializable {

    private Long numProteccion;
    private Long emisor;
    private Double importe;
    private String movimiento;

    public Long getNumProteccion() {
        return numProteccion;
    }

    public void setNumProteccion(Long numProteccion) {
        this.numProteccion = numProteccion;
    }

    public Long getEmisor() {
        return emisor;
    }

    public void setEmisor(Long emisor) {
        this.emisor = emisor;
    }

    public Double getImporte() {
        return importe;
    }

    public void setImporte(Double importe) {
        this.importe = importe;
    }

    public String getMovimiento() {
        return movimiento;
    }

    public void setMovimiento(String movimiento) {
        this.movimiento = movimiento;
    }
}

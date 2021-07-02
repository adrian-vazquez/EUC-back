package com.citi.euces.pronosticos.infra.dto;

import java.io.Serializable;
import java.util.Date;

public class RebNumProtectDTO implements Serializable {

    private Long numProteccion;
    private Date fechaMovimiento;
    private Date fechaRegistroContable;

    public Long getNumProteccion() {
        return numProteccion;
    }

    public void setNumProteccion(Long numProteccion) {
        this.numProteccion = numProteccion;
    }

    public Date getFechaMovimiento() {
        return fechaMovimiento;
    }

    public void setFechaMovimiento(Date fechaMovimiento) {
        this.fechaMovimiento = fechaMovimiento;
    }

    public Date getFechaRegistroContable() {
        return fechaRegistroContable;
    }

    public void setFechaRegistroContable(Date fechaRegistroContable) {
        this.fechaRegistroContable = fechaRegistroContable;
    }
}

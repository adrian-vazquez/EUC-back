package com.citi.euces.pronosticos.models;

import java.io.Serializable;

public class LeerArchivoRequest implements Serializable {

    private static final long serialVersionUID = 1L;


    private String file;
    private String fechaMovimiento;
    private String fechaContable;
    private String fecha;

    public String getFile() {
        return file;
    }

    public void setFile(String file) {
        this.file = file;
    }

    public String getFechaMovimiento() {
        return fechaMovimiento;
    }

    public void setFechaMovimiento(String fechaMovimiento) {
        this.fechaMovimiento = fechaMovimiento;
    }

    public String getFechaContable() {
        return fechaContable;
    }

    public void setFechaContable(String fechaContable) {
        this.fechaContable = fechaContable;
    }
    
    public String getFecha() { return fecha; }
    
    public void setFecha(String fecha) { this.fecha = fecha; }
}

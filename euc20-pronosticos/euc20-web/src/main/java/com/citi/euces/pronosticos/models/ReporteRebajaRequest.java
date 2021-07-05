package com.citi.euces.pronosticos.models;

import java.io.Serializable;

public class ReporteRebajaRequest implements Serializable {

    private static final long serialVersionUID = 1L;

    private String fecha;
    private Integer page;
    private String serch;

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        fecha = fecha;
    }

    public Integer  getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public String getSerch() {
        return serch;
    }

    public void setSerch(String serch) {
        this.serch = serch;
    }
}

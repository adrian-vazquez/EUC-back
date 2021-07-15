package com.citi.euces.pronosticos.models;

import java.io.Serializable;

public class ReporteRebajaRequest implements Serializable {

    private static final long serialVersionUID = 1L;

    private String fecha;
    private Integer page;
    private String search;

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public String getSearch() {
        return search;
    }

    public void setSearch(String search) {
        this.search = search;
    }
}

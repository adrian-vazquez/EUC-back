package com.citi.euces.pronosticos.infra.dto;

import java.io.Serializable;

public class CatServiciosPronosticosDTO implements Serializable {

    private static final long serialVersionUID = 1L;
    private Long idServicio;
    private Long idOndemand;
    private String servicio;

    public CatServiciosPronosticosDTO() {
    }

    public CatServiciosPronosticosDTO(Long idServicio, Long idOndemand, String servicio) {
        this.idServicio = idServicio;
        this.idOndemand = idOndemand;
        this.servicio = servicio;
    }

    public Long getIdServicio() {
        return idServicio;
    }

    public void setIdServicio(Long idServicio) {
        this.idServicio = idServicio;
    }

    public Long getIdOndemand() {
        return idOndemand;
    }

    public void setIdOndemand(Long idOndemand) {
        this.idOndemand = idOndemand;
    }

    public String getServicio() {
        return servicio;
    }

    public void setServicio(String servicio) {
        this.servicio = servicio;
    }
}

package com.citi.euces.pronosticos.entities;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "PPC_MIS_CATALOGO_SERVICIOS_PRONOSTICO")
public class CatServiciosPronostico implements Serializable {

    private static final long serialVersionUID = 1L;
    private final @Id
    @GeneratedValue
    Long id = null;
    @Column(name = "ID_ONDEMAND")
    private Long idOndemand;
    @Column(name = "SERVICIO")
    private String servicio;

    public Long getId() {
        return id;
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

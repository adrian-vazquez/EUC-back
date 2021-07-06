package com.citi.euces.pronosticos.entities;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "PPC_MIS_CATALOGO_CAUSAS_RECHAZO")
public class CatCausaRechazo implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "ID_CAUSA_RECHAZO")
    private Long idFlujo;
    @Column(name = "CAUSA")
    private String causa;
    @Column(name = "SOLUCION")
    private String solucion;
    @Column(name = "GENERICO")
    private String generico;
    @Column(name = "CAUSA_PROTECCION")
    private String causaProteccion;


    public Long getIdFlujo() {
        return idFlujo;
    }

    public void setIdFlujo(Long idFlujo) {
        this.idFlujo = idFlujo;
    }

    public String getCausa() {
        return causa;
    }

    public void setCausa(String causa) {
        this.causa = causa;
    }

    public String getSolucion() {
        return solucion;
    }

    public void setSolucion(String solucion) {
        this.solucion = solucion;
    }

    public String getGenerico() {
        return generico;
    }

    public void setGenerico(String generico) {
        this.generico = generico;
    }

    public String getCausaProteccion() {
        return causaProteccion;
    }

    public void setCausaProteccion(String causaProteccion) {
        this.causaProteccion = causaProteccion;
    }
}

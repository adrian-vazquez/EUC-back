package com.citi.euces.pronosticos.entities;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "PPC_MIS_REB_NUMPROTEC")
@NamedQueries({
        @NamedQuery(name = "RebNumProtect.findAll", query = "SELECT r FROM RebNumProtect r")})

public class RebNumProtect implements Serializable {

    private static final long serialVersionUID = 1L;

    private final @Id
    @GeneratedValue
    Long id = null;
    @Column(name = "NUM_PROTECCION")
    private Long numProteccion;
    @Column(name = "FEC_MOVIMIENTO")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaMovimiento;
    @Column(name = "FEC_REGISTRO_CONTABLE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaRegistroContable;

    public Long getId() {
        return id;
    }

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


    @Override
    public String toString() {
        return "RebNumProtect{" +
                "numProteccion=" + numProteccion +
                ", fechaMovimiento=" + fechaMovimiento +
                ", fechaRegistroContable=" + fechaRegistroContable +
                '}';
    }
}

package com.citi.euces.pronosticos.entities;

import javax.persistence.*;
import java.io.Serializable;

@NamedStoredProcedureQueries(
        {
                @NamedStoredProcedureQuery(name = SpRebajaMaestroDeComisiones.Name_Query_Rebajas,
                procedureName = "PPC_MIS_SP_REBAJA_MAESTRO_COMISIONES",
                parameters = {
                        @StoredProcedureParameter(type = Integer.class, mode = ParameterMode.OUT)
                })
        }
)

@Entity
public class SpRebajaMaestroDeComisiones implements Serializable {

    private static final long serialVersionUID = 1L;
    public static final String Name_Query_Rebajas="SpRebajaMaestroComisiones";

    @Id
    private Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}

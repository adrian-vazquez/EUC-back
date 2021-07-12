package com.citi.euces.pronosticos.repositories;

import com.citi.euces.pronosticos.entities.SpRebajaMaestroDeComisiones;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.stereotype.Repository;

@Repository
public interface SpRebajaMaestroDeComusionesRepository  extends JpaRepository<SpRebajaMaestroDeComisiones, Long> {

    @Procedure(name = SpRebajaMaestroDeComisiones.Name_Query_Rebajas)
    Integer spRebajaMaestroComisiones();
}

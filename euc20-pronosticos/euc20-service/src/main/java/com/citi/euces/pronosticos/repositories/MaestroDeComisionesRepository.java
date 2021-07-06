package com.citi.euces.pronosticos.repositories;

import com.citi.euces.pronosticos.entities.MaestroDeComisiones;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MaestroDeComisionesRepository extends JpaRepository<MaestroDeComisiones, Long> {

    @Query("SELECT m FROM MaestroDeComisiones m  WHERE TO_CHAR(m.fechaMovimiento, 'dd/MM/yy') = :fecha")
    List<MaestroDeComisiones> findFechaM(@Param("fecha") String fecha);

}

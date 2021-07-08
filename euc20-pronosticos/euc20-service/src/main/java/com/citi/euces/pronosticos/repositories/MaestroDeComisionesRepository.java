package com.citi.euces.pronosticos.repositories;

import com.citi.euces.pronosticos.entities.MaestroDeComisiones;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface MaestroDeComisionesRepository extends JpaRepository<MaestroDeComisiones, Long> {

    @Query("SELECT m FROM MaestroDeComisiones m  WHERE TO_CHAR(m.fechaMovimiento, 'dd/MM/yy') = :fecha")
    Page<MaestroDeComisiones> findByFechaMovimeiento(@Param("fecha") String fecha, Pageable pageable);

}

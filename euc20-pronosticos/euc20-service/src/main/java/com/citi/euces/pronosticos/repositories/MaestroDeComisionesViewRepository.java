package com.citi.euces.pronosticos.repositories;

import com.citi.euces.pronosticos.entities.MaestroDeComisionesView;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface MaestroDeComisionesViewRepository extends JpaRepository<MaestroDeComisionesView, Long> {
    @Query(value = "SELECT * FROM VW_PPC_MIS_SERCH_REPORTE_REBAJA WHERE TO_CHAR(F_MOVIMIENTO, 'dd/MM/yy') = :fecha AND  SEARCHDATA  LIKE %:searchData%" ,
            nativeQuery = true)
    Page<MaestroDeComisionesView> searchData(@Param("fecha") String fecha, @Param("searchData") String searchData, Pageable pageable);

}

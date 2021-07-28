package com.citi.euces.pronosticos.repositories;

import com.citi.euces.pronosticos.entities.MaestroDeComisiones;
import com.citi.euces.pronosticos.infra.dto.AgregarListaEstatus15DTO;
import com.citi.euces.pronosticos.infra.dto.ClientesEstatus15DTO;
import com.citi.euces.pronosticos.infra.dto.ReporteCuadreDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MaestroDeComisionesRepository extends JpaRepository<MaestroDeComisiones, Long> {

    @Query("SELECT m FROM MaestroDeComisiones m  WHERE TO_CHAR(m.fechaMovimiento, 'dd/MM/yy') = :fecha")
    Page<MaestroDeComisiones> findByFechaMovimeiento(@Param("fecha") String fecha, Pageable pageable);

    @Query("SELECT m FROM MaestroDeComisiones m  WHERE TO_CHAR(m.fechaMovimiento, 'dd/MM/yy') = :fecha")
    List<MaestroDeComisiones> findByAllFechaMovimeiento(@Param("fecha") String fecha);

    @Modifying
    @Query("UPDATE MaestroDeComisiones m SET m.id.catalogadaGc = 2 WHERE m.id.catalogadaGc = 1 AND m.id.idEstatusComision = 3 AND m.id.anio = :anio AND m.id.mes = :mes ")
    int updateCatalogadaGc(@Param("anio") Integer anio,@Param("mes") Integer mes);

    @Query(nativeQuery = true, value = "SELECT 'Cobro Especial' as tipo, " +
            "PPC_MIS_FN_CUENTA_CLIENTE_MC_CUADRE(1, :mes1 , :anio1 , :mes2, 0 , :mes3 ,0) as noCliente," +
            "PPC_MIS_FN_SUMA_COMISION_MC_CUADRE(1, :mes1 , :anio1 , :mes2, 0 , :mes3 ,0) as comision," +
            "PPC_MIS_FN_SUMA_IVA_MC_CUADRE(1, :mes1 , :anio1, :mes2, 0, :mes3, 0) as iva, " +
            "PPC_MIS_FN_SUMA_TOTAL_MC_CUADRE(1, :mes1, :anio1, :mes2, 0 , :mes3 ,0) as total from PPC_MIS_MAESTRO_COMISIONES")
    List<ReporteCuadreDTO> getFunctionRepCuadre1(@Param("mes1") Integer mes1, @Param("mes2") Integer mes2, @Param("mes3")
            Integer mes3, @Param("anio1") Integer anio1);

    @Query(nativeQuery = true, value = "SELECT 'Extra' as tipo, " +
            "PPC_MIS_FN_CUENTA_CLIENTE_MC_CUADRE(2, :mes1 , :anio1 , :mes2, :anio2 , :mes3 , :anio3) as noCliente," +
            "PPC_MIS_FN_SUMA_COMISION_MC_CUADRE(2, :mes1 , :anio1 , :mes2, :anio2 , :mes3 , :anio3) as comision," +
            "PPC_MIS_FN_SUMA_IVA_MC_CUADRE(2, :mes1 , :anio1 , :mes2, :anio2 , :mes3 , :anio3) as iva, " +
            "PPC_MIS_FN_SUMA_TOTAL_MC_CUADRE(2, :mes1 , :anio1 , :mes2, :anio2 , :mes3 , :anio3) as total from PPC_MIS_MAESTRO_COMISIONES")
    List<ReporteCuadreDTO> getFunctionRepCuadre2(@Param("mes1") Integer mes1, @Param("mes2") Integer mes2, @Param("mes3")
            Integer mes3, @Param("anio1") Integer anio1, @Param("anio2") Integer anio2, @Param("anio3") Integer anio3);

    @Query(nativeQuery = true, value = "SELECT 'Mora > 90 Dias' as tipo, " +
            "PPC_MIS_FN_CUENTA_CLIENTE_MC_CUADRE(3, 0 , :anioActual , 0 , 0 , 0 , 0 ) as noCliente," +
            "PPC_MIS_FN_SUMA_COMISION_MC_CUADRE(3, 0 , :anioActual , 0 , 0 , 0 , 0 ) as comision," +
            "PPC_MIS_FN_SUMA_IVA_MC_CUADRE(3, 0 , :anioActual , 0 , 0 , 0 , 0 ) as iva, " +
            "PPC_MIS_FN_SUMA_TOTAL_MC_CUADRE(3, 0 ,  :anioActual , 0 , 0 , 0 , 0 ) as total from PPC_MIS_MAESTRO_COMISIONES")
    List<ReporteCuadreDTO> getFunctionRepCuadre3(@Param("anioActual") Integer anioActual);


    @Query(nativeQuery = true, value = "SELECT 'Mora > 90 Dias' as tipo, " +
            "PPC_MIS_FN_CUENTA_CLIENTE_MC_CUADRE(4, 0 , :anioActual  , 0 , 0 , 0 , 0 ) as noCliente," +
            "PPC_MIS_FN_SUMA_COMISION_MC_CUADRE(4, 0 , :anioActual  , 0 , 0 , 0 , 0 ) as comision," +
            "PPC_MIS_FN_SUMA_IVA_MC_CUADRE(4, 0 , :anioActual  , 0 , 0 , 0 , 0 ) as iva, " +
            "PPC_MIS_FN_SUMA_TOTAL_MC_CUADRE(4, 0 , :anioActual  , 0 , 0 , 0 , 0 ) as total from PPC_MIS_MAESTRO_COMISIONES")
    List<ReporteCuadreDTO> getFunctionRepCuadre4(@Param("anioActual") Integer anioActual);

    @Query("SELECT m FROM MaestroDeComisiones m WHERE m.id.llave IN (:llaves)")
    List<MaestroDeComisiones> findByLlaveIN(@Param("llaves") List<Long> llaves);

    @Query("SELECT m FROM MaestroDeComisiones m WHERE m.id.llave = :llave")
    List<MaestroDeComisiones> findByLlave(@Param("llave") Long llave);
}

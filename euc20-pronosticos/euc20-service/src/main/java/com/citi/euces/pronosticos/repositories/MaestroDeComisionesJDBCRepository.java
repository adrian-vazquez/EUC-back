package com.citi.euces.pronosticos.repositories;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.citi.euces.pronosticos.infra.dto.ClientesEstatus15DTO;

@Repository
public class MaestroDeComisionesJDBCRepository {
    private final JdbcTemplate jdbcTemplate;

    public MaestroDeComisionesJDBCRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Transactional
    public List<ClientesEstatus15DTO> getClientes(Long cliente, Integer mes1, Integer anio1, Integer mes2, Integer anio2, Integer mes3, Integer anio3, Integer mes4, Integer anio4, Integer mes5, Integer anio5, Integer mes6, Integer anio6) {
        String sql = "SELECT mc.no_cliente,mc.chequera,mc.sucursal,mc.cuenta,mc.id_servicio,mc.id_ondemand,mc.mes,mc.anio,mc.p_iva,mc.m_comision," +
                "mc.m_iva,mc.m_total,mc.m_com_parcial,mc.m_iva_parcial,mc.id_estatus_comision,mc.id_causa_rechazo,mc.f_movimiento,mc.f_registro_contable," +
                "mc.llave,mc.catalogada_gc, mc.csi, mc.com_ec,mc.com_p,mc.chequera_cargo,mc.no_proteccion,mc.llave_temporal,mc.estatus_proteccion," +
                "mc.contrato,mc.f_ingreso,mc.udmer,mc.franquicia_registro, mc.id_franquicia, mc.FEC_VENC_PROTECCION, " +
                "'Cliente: ' || mc.no_cliente || ' | Chequera: ' || mc.chequera || ' | Mes: ' || mc.mes || ' | Año: ' || mc.anio || ' | Llave: ' || mc.llave || ' | Servicio: ' || cs.SERV_OPERACIONES as contenido " +
                "FROM PPC_MIS_MAESTRO_COMISIONES mc " +
                "INNER JOIN PPC_MIS_CATALOGO_SERVICIOS cs on (cs.Clave_MIS=mc.id_servicio and cs.Clave_804_274_15 = mc.id_ondemand) " +
                "and (mes = ? and anio = ? or mes = ? and anio = ? or mes = ? and anio = ? or mes = ? and anio = ? or mes = ? and anio = ? or mes = ? and anio = ?) "+
                "and no_cliente = ? "+
                "and id_estatus_comision = 3 order by anio, mes";
        //|| ' | tipo: ' || cs.SERV_OPERACIONES
        System.out.println("QUERY_SQL_Clientes :: ejecute:: " + sql);

        return jdbcTemplate.query(
                sql, new Object[]{mes1, anio1, mes2, anio2, mes3, anio3, mes4, anio4, mes5, anio5, mes6, anio6, cliente},
                (rs, rowNum) ->
                        new ClientesEstatus15DTO(
                                rs.getLong("NO_CLIENTE"),
                                rs.getString("CHEQUERA"),
                                rs.getString("SUCURSAL"),
                                rs.getString("CUENTA"),
                                rs.getInt("ID_SERVICIO"),
                                rs.getInt("ID_ONDEMAND"),
                                rs.getInt("MES"),
                                rs.getInt("ANIO"),
                                rs.getInt("P_IVA"),
                                rs.getDouble("M_COMISION"),
                                rs.getDouble("M_IVA"),
                                rs.getDouble("M_Total"),
                                rs.getDouble("M_COM_PARCIAL"),
                                rs.getDouble("M_IVA_PARCIAL"),
                                rs.getInt("ID_ESTATUS_COMISION"),
                                rs.getInt("ID_CAUSA_RECHAZO"),
                                rs.getString("F_MOVIMIENTO"),
                                rs.getString("F_REGISTRO_CONTABLE"),
                                rs.getString("LLAVE"),
                                rs.getInt("CATALOGADA_GC"),
                                rs.getString("CSI"),
                                rs.getString("COM_EC"),
                                rs.getString("COM_P"),
                                rs.getString("CHEQUERA_CARGO"),
                                rs.getInt("NO_PROTECCION"),
                                rs.getString("LLAVE_TEMPORAL"),
                                rs.getInt("ESTATUS_PROTECCION"),
                                rs.getString("CONTRATO"),
                                rs.getString("F_INGRESO"),
                                rs.getInt("UDMER"),
                                rs.getString("FRANQUICIA_REGISTRO"),
                                rs.getInt("ID_FRANQUICIA"),
                                rs.getString("FEC_VENC_PROTECCION"),
                                rs.getString("CONTENIDO")
                        )
        );
    }
}

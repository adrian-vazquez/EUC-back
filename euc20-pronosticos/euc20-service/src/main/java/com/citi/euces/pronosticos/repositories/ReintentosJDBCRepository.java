package com.citi.euces.pronosticos.repositories;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Types;
import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ParameterizedPreparedStatementSetter;
import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Repository;

import com.citi.euces.pronosticos.infra.dto.GenArcPFECyTCDTO;
import com.citi.euces.pronosticos.infra.dto.Maestro_ComisionesBEDTO;
import com.citi.euces.pronosticos.infra.dto.RechazosFileDTO;
import com.citi.euces.pronosticos.infra.dto.ReintentosMaestroComiDTO;
import com.citi.euces.pronosticos.infra.dto.TbBloqueosBEDTO;
import com.citi.euces.pronosticos.infra.exceptions.GenericException;

@Repository
public class ReintentosJDBCRepository {

	private final JdbcTemplate jdbcTemplate;
    private SimpleJdbcCall simpleJdbcCallRefCursor;
    static final Logger log = LoggerFactory.getLogger(RebajaPronosticoJDBCRepository.class);

	public ReintentosJDBCRepository(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	@Transactional
	public void truncateTableReintentos() {
		String query = "DELETE FROM PPC_MIS_REINTENTOS";
		jdbcTemplate.execute(query);
	}

	@Transactional
	public List<GenArcPFECyTCDTO> ConteoReintentosSemanalesPersonaFisica(Integer mes1, Integer anio1, Integer dias1,
			Integer mes2, Integer anio2, Integer dias2, Integer mes3, Integer anio3, Integer dias3) {

		String sql = "SELECT COUNT(*) AS conteo " + "FROM PPC_MIS_MAESTRO_COMISIONES mc "
				+ "WHERE mc.ID_ESTATUS_COMISION=3 AND mc.CATALOGADA_GC=5 AND mc.ID_SERVICIO=10 AND mc.ID_ONDEMAND=32"
				+ " AND (";

		if (dias1 > 0)
			sql = sql + "mc.MES=" + mes1 + " AND mc.ANIO=" + anio1;
		if (dias2 > 0)
			sql = sql + " OR mc.MES=" + mes2 + " AND mc.ANIO=" + anio2;
		if (dias3 > 0)
			sql = sql + " OR mc.MES=" + mes3 + " AND mc.ANIO=" + anio3;

		sql = sql.replace("( OR", "(");

		sql = sql + " )";

		System.out.println("QUERY_SQL_Clientes :: ejecute:: " + sql);

		return jdbcTemplate.query(sql, (cc, rowNum) -> new GenArcPFECyTCDTO(cc.getInt("CONTEO")));
	}

	@Transactional
	public List<GenArcPFECyTCDTO> ConteoReintentosSemanalesExtraContable(Integer mes1, Integer anio1, Integer dias1,
			Integer mes2, Integer anio2, Integer dias2, Integer mes3, Integer anio3, Integer dias3) {

		String sql = "SELECT COUNT(*) AS conteo " + "FROM PPC_MIS_MAESTRO_COMISIONES mc "
				+ "WHERE mc.ID_ESTATUS_COMISION=3 AND mc.CATALOGADA_GC=1" + " AND (";

		if (dias1 > 0)
			sql = sql + "mc.MES=" + mes1 + " AND mc.ANIO=" + anio1;
		if (dias2 > 0)
			sql = sql + " OR mc.MES=" + mes2 + " AND mc.ANIO=" + anio2;
		if (dias3 > 0)
			sql = sql + " OR mc.MES=" + mes3 + " AND mc.ANIO=" + anio3;

		sql = sql.replace("( OR", "(");

		sql = sql + " )";

		System.out.println("QUERY_SQL_Clientes :: ejecute:: " + sql);

		return jdbcTemplate.query(sql, (cc, rowNum) -> new GenArcPFECyTCDTO(cc.getInt("CONTEO")));
	}

	@Transactional
	public List<GenArcPFECyTCDTO> ConteoReintentosSemanalesTasaCero() {

		String sql = "SELECT COUNT(*) AS conteo " + "FROM PPC_MIS_MAESTRO_COMISIONES mc "
				+ "WHERE mc.ID_ESTATUS_COMISION=3 AND mc.CATALOGADA_GC=5 AND (mc.ID_SERVICIO=21 and mc.ID_ONDEMAND=31);";

		System.out.println("QUERY_SQL_Clientes :: ejecute:: " + sql);

		return jdbcTemplate.query(sql, (cc, rowNum) -> new GenArcPFECyTCDTO(cc.getInt("CONTEO")));
	}

	@Transactional
	public void TruncateReintentosPerFis() {
		String query = "DELETE FROM PPC_MIS_REINTENTOS_PER_FIS";
		jdbcTemplate.execute(query);
	}

	@Transactional
	public List<ReintentosMaestroComiDTO> ReintentosSemanalesPersonaFisicaSelect(Integer mes1, Integer anio1,
			Integer mes2, Integer anio2, Integer mes3, Integer anio3) {

		String sql = "SELECT no_cliente,chequera,sucursal,cuenta,id_servicio,id_ondemand,mes,anio,p_iva,m_comision,m_iva,m_total,m_com_parcial,m_iva_parcial,id_estatus_comision,id_causa_rechazo,f_movimiento,f_registro_contable,llave,catalogada_gc, csi, com_ec,com_p,chequera_cargo,no_proteccion,llave_temporal,mc,estatus_proteccion,contrato,f_ingreso,udmer,franquicia_registro, id_franquicia "
				+ "FROM PPC_MIS_MAESTRO_COMISIONES "
				+ "WHERE id_estatus_comision=3 and catalogada_gc=5 and id_servicio=10 and id_ondemand=32" + 
				" and (mes=" + mes1 + " and anio=" + anio1 + 
				" or mes=" + mes2 + " and anio=" + anio2 + 
				" or mes=" + mes3 + " and anio=" + anio3 + ")" + 
				" order by llave ";

		System.out.println("QUERY_SQL_reintentos semanales :: ejecute:: " + sql);

		return jdbcTemplate.query(sql, (rs, rowNum) 
				-> new ReintentosMaestroComiDTO(
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
                                rs.getString("MC"),
                                rs.getInt("ESTATUS_PROTECCION"),
                                rs.getString("CONTRATO"),
                                rs.getString("F_INGRESO"),
                                rs.getInt("UDMER"),
                                rs.getString("FRANQUICIA_REGISTRO"),
                                rs.getInt("ID_FRANQUICIA")
                        )
				);
	}
	
	

	@Transactional
    public int[][] ReintentosSemanalesPersonaFisicaInsert(List<ReintentosMaestroComiDTO> books, int batchSize) throws GenericException {
		try {
			int[][] updateCounts = jdbcTemplate.batchUpdate(
	                "INSERT INTO PPC_MIS_REINTENTOS_PER_FIS(no_cliente,chequera,sucursal,cuenta,id_servicio,id_ondemand,mes,anio,p_iva,m_comision,m_iva,m_total,m_com_parcial,m_iva_parcial,id_estatus_comision,id_causa_rechazo,f_movimiento,f_registro_contable,llave,catalogada_gc, csi, com_ec,com_p,chequera_cargo,no_proteccion,llave_temporal,mc,estatus_proteccion,contrato,f_ingreso,udmer,franquicia_registro, id_franquicia) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)",
	                books,
	                batchSize,
	                new ParameterizedPreparedStatementSetter<ReintentosMaestroComiDTO>() {
	                    public void setValues(PreparedStatement ps, ReintentosMaestroComiDTO content) throws SQLException {
	                    	try {
	                    		ps.setLong(1, content.getNoCliente());
		                    	ps.setString(2, content.getChequera());
		                    	ps.setString(3, content.getSucursal());
		                    	ps.setString(4, content.getCuenta());
		                    	ps.setInt(5, content.getIdServicio());
		                    	ps.setInt(6, content.getIdOndemand());
		                    	ps.setInt(7, content.getMes());
		                    	ps.setInt(8, content.getAnio());
		                    	ps.setInt(9, content.getpIva());
		                    	ps.setDouble(10, content.getmComision());
		                    	ps.setDouble(11, content.getmIva());
		                    	ps.setDouble(12, content.getmTotal());
		                    	ps.setDouble(13, content.getmComParcial());
		                    	ps.setDouble(14, content.getmIvaParcial());
		                    	ps.setInt(15, content.getIdEstatusComision());
		                    	ps.setInt(16, content.getIdCausaRechazo());
		                    	ps.setString(17, content.getfMovimiento());
		                    	ps.setString(18, content.getfRegistroContable());
		                    	ps.setString(19, content.getLlave());
		                    	ps.setInt(20, content.getCatalogadaGc());
		                    	ps.setString(21, content.getCsi());
		                    	ps.setString(22, content.getComEc());
		                    	ps.setString(23, content.getComP());
		                    	ps.setString(24, content.getChequeraCargo());
		                    	ps.setInt(25, content.getNoProteccion());
		                    	ps.setString(26, content.getLlaveTemporal());
		                    	ps.setString(27, content.getMc());
		                    	ps.setInt(28, content.getEstatusProteccion());
		                    	ps.setString(29, content.getContrato());
		                    	ps.setString(30, content.getfIngreso());
		                    	ps.setInt(31, content.getUdemer());
		                    	ps.setString(32, content.getFraqnuiciaRegistro());
		                    	ps.setInt(33, content.getIdFranquicia());
	                    	} catch (SQLException e) {
	                			e.printStackTrace();
	                        }
	                    }
	                });
	        return updateCounts;	
		} catch (Exception e) {
			e.printStackTrace();
            throw new GenericException( "Error al guardar en :: PPC_MIS_REINTENTOS_PER_FIS ", HttpStatus.NOT_FOUND.toString());
        }
	}
	

	@Transactional
    public String PlanchaAlternasReintentoPIF() throws GenericException{
		try {
			jdbcTemplate.setResultsMapCaseInsensitive(true);
	        simpleJdbcCallRefCursor = new SimpleJdbcCall(jdbcTemplate)
	                .withProcedureName("PPC_MIS_SP_UPDATE_REINTENTOS_ALTERNAS_PIF")
	                .declareParameters(new SqlParameter("p_RegCargados", Types.INTEGER),
	                new SqlOutParameter("p_RegCargados", Types.INTEGER));
	        Map<String, Object> out = simpleJdbcCallRefCursor.execute(new MapSqlParameterSource("p_RegCargados", 0));
	        log.info("PlanchaAlternasReintentoPIF p_RegCargados :: >> " +  out.get("p_RegCargados"));
	        return out.get("p_RegCargados").toString();	
		}catch (Exception e) {
			e.printStackTrace();
            throw new GenericException( "Error al ejecutar PPC_MIS_SP_UPDATE_REBAJA_PRONOSTICOS_EV :: ", HttpStatus.NOT_FOUND.toString());
        }
    }
	

	@Transactional
    public String PlanchaAlternasReintentoextra() throws GenericException{
		try {
			jdbcTemplate.setResultsMapCaseInsensitive(true);
	        simpleJdbcCallRefCursor = new SimpleJdbcCall(jdbcTemplate)
	                .withProcedureName("PPC_MIS_SP_UPDATE_REINTENTOS_ALTERNAS_EXTRA")
	                .declareParameters(new SqlParameter("p_RegCargados", Types.INTEGER),
	                new SqlOutParameter("p_RegCargados", Types.INTEGER));
	        Map<String, Object> out = simpleJdbcCallRefCursor.execute(new MapSqlParameterSource("p_RegCargados", 0));
	        log.info("PlanchaAlternasReintentoextra p_RegCargados :: >> " +  out.get("p_RegCargados"));
	        return out.get("p_RegCargados").toString();	
		}catch (Exception e) {
			e.printStackTrace();
            throw new GenericException( "Error al ejecutar PPC_MIS_SP_UPDATE_REBAJA_PRONOSTICOS_EV :: ", HttpStatus.NOT_FOUND.toString());
        }
    }
	

	@Transactional
    public String PlanchaCheuqeraFija() throws GenericException{
		try {
			jdbcTemplate.setResultsMapCaseInsensitive(true);
	        simpleJdbcCallRefCursor = new SimpleJdbcCall(jdbcTemplate)
	                .withProcedureName("PPC_MIS_SP_UPDATE_REINTENTOS_CHEQUERAFIJA")
	                .declareParameters(new SqlParameter("p_RegCargados", Types.INTEGER),
	                new SqlOutParameter("p_RegCargados", Types.INTEGER));
	        Map<String, Object> out = simpleJdbcCallRefCursor.execute(new MapSqlParameterSource("p_RegCargados", 0));
	        log.info("PlanchaCheuqeraFija p_RegCargados :: >> " +  out.get("p_RegCargados"));
	        return out.get("p_RegCargados").toString();	
		}catch (Exception e) {
			e.printStackTrace();
            throw new GenericException( "Error al ejecutar PPC_MIS_SP_UPDATE_REBAJA_PRONOSTICOS_EV :: ", HttpStatus.NOT_FOUND.toString());
        }
    }

	
	@Transactional
	public List<Maestro_ComisionesBEDTO> ReintentosSemanalesPersonaFisicaQuery() {

		String sql = "SELECT no_cliente,chequera,sucursal,cuenta,id_servicio,id_ondemand,mes,anio,p_iva,m_comision,m_iva,m_total,m_com_parcial,m_iva_parcial,id_estatus_comision,id_causa_rechazo,f_movimiento,f_registro_contable,llave,catalogada_gc, csi, com_ec,com_p,chequera_cargo,no_proteccion,llave_temporal,mc,estatus_proteccion,contrato,f_ingreso,udmer,franquicia_registro, id_franquicia "
				+ "FROM PPC_MIS_REINTENTOS_PER_FIS order by mes, no_cliente";

		System.out.println("QUERY_SQL_reintentos semanales :: ejecute:: " + sql);

		return jdbcTemplate.query(sql, (rs, rowNum) 
				-> new Maestro_ComisionesBEDTO(
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
                                rs.getLong("NO_PROTECCION"),
                                rs.getString("LLAVE_TEMPORAL"),
                                rs.getString("MC"),
                                rs.getInt("ESTATUS_PROTECCION"),
                                rs.getString("CONTRATO"),
                                rs.getString("F_INGRESO"),
                                rs.getInt("UDMER"),
                                rs.getString("FRANQUICIA_REGISTRO"),
                                rs.getInt("ID_FRANQUICIA")
                        )
				);
	}
	

	@Transactional
	public List<TbBloqueosBEDTO> ObtenerListaBloqueos() {

		String sql = "SELECT no_cliente, sucursal, cuenta, mes, anio, descripcion, servicio "
				+ "FROM PPC_MIS_BLOQUEOS order by id_bloqueo asc ";

		System.out.println("QUERY_SQL_reintentos semanales :: ejecute:: " + sql);

		return jdbcTemplate.query(sql, (rs, rowNum) 
				-> new TbBloqueosBEDTO(
                                rs.getLong("NO_CLIENTE"),
                                rs.getLong("SUCURSAL"),
                                rs.getLong("CUENTA"),
                                rs.getString("MES"),
                                rs.getString("ANIO"),
                                rs.getString("DESCRIPCION"),
                                rs.getString("SERVICIO")
                                
						)
				);
	}
	
}

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

import com.citi.euces.pronosticos.infra.dto.RechazosFileDTO;
import com.citi.euces.pronosticos.infra.dto.RespuestasFileDTO;
import com.citi.euces.pronosticos.infra.exceptions.GenericException;

@Repository
public class RespPronosticosTmpJDBCRepository {

	private final JdbcTemplate jdbcTemplate;
	private SimpleJdbcCall simpleJdbcCallRefCursor;
	static final Logger log = LoggerFactory.getLogger(RespPronosticosTmpJDBCRepository.class);
	
	public RespPronosticosTmpJDBCRepository(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
	
	public void DLBorrarRespPronosticos() {
        String query = "DELETE FROM PPC_MIS_RESP_PRONOSTICOS_TMP";
        jdbcTemplate.execute(query);
    }
	
    public int[][] batchInsert(List<RespuestasFileDTO> books, int batchSize) throws GenericException {
		try {
			int[][] updateCounts = jdbcTemplate.batchUpdate(
	                "INSERT INTO PPC_MIS_RESP_PRONOSTICOS_TMP( NO_CLIENTE, CTA_CLIENTE, CONTRATO, IMP_OPERACION_1, IMP_OPERACION_2, COD_OPERACION, DESC_RECHAZO, LEYENDA_EMISOR, FEC_OPERACION, "
	                + "NUM_PROTECCION, SECUENCIAL, FEC_REAL, FRANQUICIA, FEC_VENCIMIENTO, SEC_ARC, SEC_INT, NOM_FRANQUICIA) "
	                + " VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)",
	                books,
	                batchSize,
	                new ParameterizedPreparedStatementSetter<RespuestasFileDTO>() {
	                    public void setValues(PreparedStatement ps, RespuestasFileDTO content) throws SQLException {
	                    	ps.setInt(1, content.getNoCliente());
	                    	ps.setString(2, content.getCtaCliente());
	                    	ps.setString(3, content.getContrato());
	                    	ps.setDouble(4, content.getImpOperacion1());
	                    	ps.setDouble(5, content.getImpOperacion2());
	                    	ps.setString(6, content.getCodOperacion());
	                    	ps.setString(7, content.getDescRechazo());
	                    	ps.setString(8, content.getLeyendaEmisor());
	                    	ps.setDate(9, new Date(content.getFecOperacion().getTime()));
	                    	ps.setString(10, content.getNumProteccion());
	                    	ps.setInt(11, content.getSecuencial());
	                    	ps.setDate(12, new Date(content.getFecReal().getTime()));
	                    	ps.setString(13, content.getFranquicia());
	                    	ps.setDate(14, new Date(content.getFecVencimiento().getTime()));
	                    	ps.setInt(15, content.getSecArc());
	                    	ps.setInt(16, content.getSecInt());
	                    	ps.setString(17, content.getNomFranquicia());
	                    }
	                });
	        return updateCounts;	
		}catch (Exception e) {
			e.printStackTrace();
            throw new GenericException( "Error al guardar en :: PPC_MIS_RESP_PRONOSTICOS_TMP ", HttpStatus.NOT_FOUND.toString());
        }
	}
	
	@Transactional
    public String updatePronosticosRespuesta() throws GenericException{
		try {
			jdbcTemplate.setResultsMapCaseInsensitive(true);
	        simpleJdbcCallRefCursor = new SimpleJdbcCall(jdbcTemplate)
	                .withProcedureName("PPC_MIS_UPDATE_PRONOSTICO_RESPUESTA")
	                .declareParameters(new SqlParameter("p_numRegCargados", Types.INTEGER),
	                new SqlOutParameter("p_numRegCargados", Types.INTEGER));
	        Map<String, Object> out = simpleJdbcCallRefCursor.execute(new MapSqlParameterSource("p_numRegCargados", 0));
	        log.info("updatePronosticosRespuesta p_numRegCargados :: >> " +  out.get("p_numRegCargados"));
	        return out.get("p_numRegCargados").toString();	
		}catch (Exception e) {
			e.printStackTrace();
            throw new GenericException( "Error al ejecutar PPC_MIS_UPDATE_PRONOSTICO_RESPUESTA :: ", HttpStatus.NOT_FOUND.toString());
        }
    }
	
	@Transactional
    public void updateCobrosResp() throws GenericException{
		try {
			jdbcTemplate.update("{call PPC_MIS_SP_UPD_COBROS_RESP_AUTO()}");
		} catch (Exception e) {
            throw new GenericException( "Error al ejecutar el SP PPC_MIS_SP_UPD_COBROS_RESP_AUTO:: " , HttpStatus.NOT_FOUND.toString());
        }
    }
}

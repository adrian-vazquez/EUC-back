package com.citi.euces.pronosticos.repositories;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ParameterizedPreparedStatementSetter;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Repository;
import com.citi.euces.pronosticos.infra.dto.RechazosFileDTO;
import com.citi.euces.pronosticos.infra.exceptions.GenericException;

@Repository
public class PronosticosTmpJDBCRepository {

	private final JdbcTemplate jdbcTemplate;
	private SimpleJdbcCall simpleJdbcCallRefCursor;
	static final Logger log = LoggerFactory.getLogger(PronosticosTmpJDBCRepository.class);
	
	public PronosticosTmpJDBCRepository(JdbcTemplate jdbcTemplate) {
		super();
		this.jdbcTemplate = jdbcTemplate;
	}
	
	@Transactional
    public int[][] batchInsert(List<RechazosFileDTO> books, int batchSize) {
        System.out.println("repository :: content :: " + books.size());

        int[][] updateCounts = jdbcTemplate.batchUpdate(
                "INSERT INTO PPC_MIS_PRONOSTICOS_TMP(ID, CLIENTE, BLANCO, CUENTA, IMPORTE, IVA, STATUS, MES, ANIO, SERVICIO, CSI, COM, COM_SIN_IVA, "
                + "IVAA, TOTAL, TIPO_COMISION, LLAVE, EJE, CATALOGADA, SECUENCIAL, FECHA, CONCEPTO, LEYENDA, DIA, ID_SERVICIO, ID_ONDEMAND, EVALUACION_VIRTUAL, OPEN_ITEM) "
                + "values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)",
                books,
                batchSize,
                new ParameterizedPreparedStatementSetter<RechazosFileDTO>() {
                    public void setValues(PreparedStatement ps, RechazosFileDTO content) throws SQLException {
                    	ps.setInt(1, content.getId());
                    	ps.setInt(2, content.getCliente());
                    	ps.setString(3, content.getBlanco());
                    	ps.setInt(4, content.getCuenta());
                    	ps.setDouble(5, content.getImporte());
                    	ps.setInt(6, content.getIva());
                    	ps.setString(7, content.getStatus());
                    	ps.setString(8, content.getMes());
                    	ps.setInt(9, content.getAnio());
                    	ps.setString(10, content.getServicio());
                    	ps.setString(11, content.getCsi());
                    	ps.setDouble(12, content.getCom());
                    	ps.setDouble(13, content.getComSinIva());
                    	ps.setDouble(14, content.getIvaa());
                    	ps.setDouble(15, content.getTotal());
                    	ps.setInt(16, content.getTipoComision());
                    	ps.setString(17, content.getLlave());
                    	ps.setString(18, content.getEje());
                    	ps.setString(19, content.getCatalogada());
                    	ps.setString(20, content.getSecuencial());
                    	ps.setDate(21, (Date) content.getFecha());
                    	ps.setString(22, content.getConcepto());
                    	ps.setString(23, content.getLeyenda());
                    	ps.setInt(24, content.getDias());
                    	ps.setLong(25, content.getIdServicio());
                    	ps.setLong(26, content.getIdOndemand());
                    	ps.setInt(27, content.getEvaluacionVirtual());
                    	ps.setString(28, content.getOpenItem());
                    }
                });
        return updateCounts;
	}
	
	@Transactional
	public void BorrarDLDatosPronosticosTmp() {
        String query = "DELETE FROM PPC_MIS_PRONOSTICOS_TMP";
        jdbcTemplate.execute(query);
    }
	
	@Transactional
    public void updateCobrosEsp() throws GenericException{
		try {
	        jdbcTemplate.update("call PPC_MIS_SP_UPDATE_COBRO_ESP");
		} catch (Exception e) {
            throw new GenericException( "Error al actualizar cobro especial:: " , HttpStatus.NOT_FOUND.toString());
        }
    }
	
	@Transactional
    public void updateExtraCont() throws GenericException{
		try {
	        jdbcTemplate.update("call PPC_MIS_SP_UPDATE_EXTRA_CONT");
		} catch (Exception e) {
            throw new GenericException( "Error al actualizar extra contable:: " , HttpStatus.NOT_FOUND.toString());
        }
    }
}

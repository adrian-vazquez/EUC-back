package com.citi.euces.pronosticos.repositories;


import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ParameterizedPreparedStatementSetter;
import org.springframework.stereotype.Repository;

import com.citi.euces.pronosticos.infra.dto.PronosticosFinalDTO;
import com.citi.euces.pronosticos.infra.dto.RechazosFileDTO;
import com.citi.euces.pronosticos.infra.exceptions.GenericException;


@Repository
public class PronosticosTmpJDBCRepository {

	private final JdbcTemplate jdbcTemplate;
	static final Logger log = LoggerFactory.getLogger(PronosticosTmpJDBCRepository.class);
	
	public PronosticosTmpJDBCRepository(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
	
	public List<PronosticosFinalDTO> selectPronosticosFinal()
	{
		String sql = "SELECT ID, CLIENTE, BLANCO, CUENTA, IMPORTE, IVA, STATUS, MES, ANIO, SERVICIO, CSI, COM, COM_SIN_IVA,"
				+ "IVAA, TOTAL, TIPO_COMISION, LLAVE, EJE, CATALOGADA, SECUENCIAL, FECHA, CONCEPTO, LEYENDA, "
				+ "DIAS , ID_SERVICIO, ID_ONDEMAND, EVALUACION_VIRTUAL, "
				+ "CTA_CLIENTE, CONTRATO, FRANQUICIA, NUM_PROTECCION, FEC_VENC_PROTECCION, OPEN_ITEM "
				+ "FROM PPC_MIS_PRONOSTICOS_TMP WHERE 1";
        return jdbcTemplate.query(sql, (cc, rowNum) ->
                new PronosticosFinalDTO(
                        cc.getInt("ID"),
                        cc.getInt("CLIENTE"),
                        cc.getString("BLANCO"),
                        cc.getLong("CUENTA"),
                        cc.getDouble("IMPORTE"),
                        cc.getInt("IVA"),
                        cc.getString("STATUS"),
                        cc.getString("MES"),
                        cc.getInt("ANIO"),
                        cc.getString("SERVICIO"),
                        cc.getString("CSI"),
                        cc.getString("COM"),
                        cc.getDouble("COM_SIN_IVA"),
                        cc.getDouble("IVAA"),
                        cc.getDouble("TOTAL"),
                        cc.getInt("TIPO_COMISION"),
                        cc.getString("LLAVE"),
                        cc.getString("EJE"),
                        cc.getString("CATALOGADA"),
                        cc.getString("SECUENCIAL"),
                        cc.getDate("FECHA"),
                        cc.getString("CONCEPTO"),
                        cc.getString("LEYENDA"),
                        cc.getInt("DIAS"),
                        cc.getLong("ID_SERVICIO"),
                        cc.getLong("ID_ONDEMAND"),
                        cc.getInt("EVALUACION_VIRTUAL"),
                        cc.getString("CTA_CLIENTE"),
                        cc.getString("CONTRATO"),
                        cc.getString("FRANQUICIA"),
                        cc.getString("NUM_PROTECCION"),
                        cc.getDate("FEC_VENC_PROTECCION"),
                        cc.getString("OPEN_ITEM")
                )
        );
	}
	
	@Transactional
    public int[][] batchInsert(List<RechazosFileDTO> books, int batchSize) throws GenericException {
		try {
			int[][] updateCounts = jdbcTemplate.batchUpdate(
	                "INSERT INTO PPC_MIS_PRONOSTICOS_TMP(ID, CLIENTE, BLANCO, CUENTA, IMPORTE, IVA, STATUS, MES, ANIO, SERVICIO, CSI, COM, COM_SIN_IVA, IVAA, TOTAL, TIPO_COMISION, LLAVE, EJE, CATALOGADA, "
	                + "SECUENCIAL, FECHA, CONCEPTO, LEYENDA, DIAS, ID_SERVICIO, ID_ONDEMAND, OPEN_ITEM) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)",
	                books,
	                batchSize,
	                new ParameterizedPreparedStatementSetter<RechazosFileDTO>() {
	                    public void setValues(PreparedStatement ps, RechazosFileDTO content) throws SQLException {
	                    	try {
	                    		ps.setInt(1, content.getId());
		                    	ps.setInt(2, content.getCliente());
		                    	ps.setString(3, content.getBlanco());
		                    	ps.setLong(4, content.getCuenta());
		                    	ps.setDouble(5, content.getImporte());
		                    	ps.setInt(6, content.getIva());
		                    	ps.setString(7, content.getStatus());
		                    	ps.setString(8, content.getMes());
		                    	ps.setInt(9, content.getAnio());
		                    	ps.setString(10, content.getServicio());
		                    	ps.setString(11, content.getCsi());
		                    	ps.setString(12, content.getCom());
		                    	ps.setDouble(13, content.getComSinIva());
		                    	ps.setDouble(14, content.getIvaa());
		                    	ps.setDouble(15, content.getTotal());
		                    	ps.setInt(16, content.getTipoComision());
		                    	ps.setString(17, content.getLlave());
		                    	ps.setString(18, content.getEje());
		                    	ps.setString(19, content.getCatalogada());
		                    	ps.setString(20, content.getSecuencial());
		                    	ps.setDate(21, new Date(content.getFecha().getTime()));
		                    	ps.setString(22, content.getConcepto());
		                    	ps.setString(23, content.getLeyenda());
		                    	ps.setInt(24, content.getDias());
		                    	ps.setLong(25, content.getIdServicio());
		                    	ps.setLong(26, content.getIdOndemand());
		                    	ps.setString(27, content.getOpenItem());	
	                    	} catch (SQLException e) {
	                			e.printStackTrace();
	                        }
	                    }
	                });
	        return updateCounts;	
		} catch (Exception e) {
			e.printStackTrace();
            throw new GenericException( "Error al guardar en :: PPC_MIS_PRONOSTICOS_TMP ", HttpStatus.NOT_FOUND.toString());
        }
	}
	
	@Transactional
	public void BorrarDLDatosPronosticosTmp() {
        String query = "DELETE FROM PPC_MIS_PRONOSTICOS_TMP";
        jdbcTemplate.execute(query);
    }
	
    public void updateCobrosEsp() throws GenericException{
		try {
	        jdbcTemplate.update("{call PPC_MIS_SP_UPDATE_COBRO_ESP()}");
		} catch (Exception e) {
			e.printStackTrace();
            throw new GenericException( "Error al actualizar cobro especial:: " , HttpStatus.NOT_FOUND.toString());
        }
    }
	
    public void updateExtraCont() throws GenericException{
		try {
	        jdbcTemplate.update("{call PPC_MIS_SP_UPDATE_EXTRA_CONT()}");
		} catch (Exception e) {
			e.printStackTrace();
            throw new GenericException( "Error al actualizar extra contable:: " , HttpStatus.NOT_FOUND.toString());
        }
    }
}

package com.citi.euces.pronosticos.repositories;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;


import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ParameterizedPreparedStatementSetter;
import org.springframework.stereotype.Repository;

import com.citi.euces.pronosticos.infra.dto.RechazosFileDTO;
import com.citi.euces.pronosticos.infra.exceptions.GenericException;

@Repository
public class PronosticosAltCheqJDBCRepository {

	private final JdbcTemplate jdbcTemplate;

	public PronosticosAltCheqJDBCRepository(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
	
    public int[][] batchInsert(List<RechazosFileDTO> books, int batchSize) throws GenericException {
    	try {
    		int[][] updateCounts = jdbcTemplate.batchUpdate(
                    "INSERT INTO PPC_MIS_PRONOSTICOS_ALT_CHEQ(ID, CLIENTE, BLANCO, CUENTA, IMPORTE, IVA, STATUS, MES, ANIO, SERVICIO, CSI, COM, COM_SIN_IVA, "
                            + "IVAA, TOTAL, TIPO_COMISION, LLAVE, EJE, CATALOGADA, SECUENCIAL, FECHA, CONCEPTO, LEYENDA, DIAS, ID_SERVICIO, ID_ONDEMAND) "
                            + " VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)",
                    books,
                    batchSize,
                    new ParameterizedPreparedStatementSetter<RechazosFileDTO>() {
                        public void setValues(PreparedStatement ps, RechazosFileDTO content) throws SQLException {
                    	try{
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
                        } catch (SQLException e) {
                			e.printStackTrace();
                        }
                        }
                    });
            return updateCounts;	
    	} catch (Exception e) {
    		e.printStackTrace();
            throw new GenericException( "Error al guardar en PronosticosTmp :: " , HttpStatus.NOT_FOUND.toString());
        }
	}
	

	public void BorrarDLDatosPronosticosAltCheq() throws GenericException {
		try {
			String query = "DELETE FROM PPC_MIS_PRONOSTICOS_ALT_CHEQ";
	        jdbcTemplate.execute(query);	
		} catch (Exception e) {
    		e.printStackTrace();
            throw new GenericException( "Error al eliminar los datos de PPC_MIS_PRONOSTICOS_ALT_CHEQ :: " , HttpStatus.NOT_FOUND.toString());
        }
    }
	
}

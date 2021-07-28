package com.citi.euces.pronosticos.repositories;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.Date;
import java.util.List;
import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ParameterizedPreparedStatementSetter;
import org.springframework.stereotype.Repository;
import com.citi.euces.pronosticos.infra.dto.ImpReporteCobroDTO;
import com.citi.euces.pronosticos.infra.dto.SlunifinalexcPerfDTO;
import com.citi.euces.pronosticos.infra.utils.FormatUtils;

@Repository
public class PerfilesJDBCRepository {
	static final Logger log = LoggerFactory.getLogger(PerfilesJDBCRepository.class);
	
	private final JdbcTemplate jdbcTemplate;

    public PerfilesJDBCRepository(JdbcTemplate jdbcTemplate) {
    	this.jdbcTemplate = jdbcTemplate;
	}
 
		public void deleteSlunifinalexcPerf() {
			String query1 = "DELETE FROM PPC_PCB_SLUNIFINALEXC_PERF";
			jdbcTemplate.execute(query1);
		}		
		public void deleteLayoutCarga() {
			String query2 = "DELETE FROM PPC_PCB_LAYOUT_CARGA";
			jdbcTemplate.execute(query2);
		}
		public void deleteArmadoCuerpo() {
			String query3 = "DELETE FROM PPC_PCB_ARMADO_CUERPO";
			jdbcTemplate.execute(query3);
		}
    	public void deletePreparoCuerpo() {
    		String query4 = "DELETE FROM PPC_PCB_PREPARO_CUERPO";
    		jdbcTemplate.execute(query4);
    	}
    	public void deleteHeader() {
    		String query5 = "DELETE FROM PPC_PCB_HEADER";
    		jdbcTemplate.execute(query5);
    	}
    	public void deleteTrailer() {
    		String query6 = "DELETE FROM PPC_PCB_TRAILER";
    		jdbcTemplate.execute(query6);
    	}
    	public void deleteTablaErrores() {
    		String query7 = "DELETE FROM PPC_PCB_TABLA_ERRORES";
    		jdbcTemplate.execute(query7);
    	}
    	public void deletePreparoRespuesta() {
    		String query8 = "DELETE FROM PPC_PCB_PREPARO_RESPUESTA";
    		jdbcTemplate.execute(query8);
    	}
    	public void deleteTmpRespuesta() {
        	String query9 = "DELETE FROM PPC_PCB_TMPRESPUESTA";
        	jdbcTemplate.execute(query9);
        }
    	public void deleteComPendOper() {
    		String query10 = "DELETE FROM PPC_PCB_COM_PEND_OPER";
    		jdbcTemplate.execute(query10);
    	}
    	public void insertComPendOper() {
    		String query = "INSERT INTO PPC_PCB_COM_PEND_OPER (CONSECUTIVO, NO_CLIENTES, BLANCO, CUENTA, "
    				+ "IMPORTE, IVA, CAUSA_RECHAZO, MES, ANIO, SERVICIO, CSI, COM, COM_SIN_IVA, IVAA, TOTAL, "
    				+ "LLAVE, EJE, CATALOGADA, F_RECHAZO) "
    				+ "SELECT "
    				+ "CONSECUTIVO AS Expr1, NO_CLIENTES AS Expr2, "
    				+ "BLANCO AS Expr3, CUENTA AS Expr4, "
    				+ "IMPORTE AS Expr5, IVA AS Expr6, "
    				+ "CAUSA_RECHAZO AS Expr7, MES AS Expr8, "
    				+ "ANIO AS Expr9, SERVICIO AS Expr10, "
    				+ "CSI AS Expr11, COM AS Expr12, "
    				+ "COM_SIN_IVA AS Expr13, IVAA AS Expr14, "
    				+ "TOTAL AS Expr15, LLAVE AS Expr16, "
    				+ "EJE AS Expr17, CATALOGADA AS Expr18, "
    				+ "F_RECHAZO AS Expr19 "
    				+ "FROM PPC_PCB_COM_PEND_OPER_PERF";
    	}
    
    @Transactional
    public int[][] insertSlunifinalexcPerf(List<SlunifinalexcPerfDTO> books, int batchSize) {
        int [][]updateCounts = jdbcTemplate.batchUpdate(
        		"INSERT INTO PPC_PCB_SLUNIFINALEXC_PERF(NUM_CLIENTE, NUM_CONTRATO, SUC, CUENTA, COM, LLAVE_PRE) VALUES(?,?,?,?,?,?)",
        		books,
                batchSize,
                new ParameterizedPreparedStatementSetter<SlunifinalexcPerfDTO>() {
                    public void setValues(PreparedStatement ps, SlunifinalexcPerfDTO argument) throws SQLException {
                    	
                        ps.setLong(1, argument.getNumCliente());
                        ps.setLong(2, argument.getNumContrato());
                        ps.setLong(3, argument.getSuc());
                        ps.setLong(4, argument.getCuenta());
                        ps.setDouble(5, argument.getCom());
                        ps.setString(6, argument.getLlavePre());
                    }
                });
        return updateCounts;
    }
    
    @Transactional
    public List<ImpReporteCobroDTO> ObtenerConsulta(String fechaCobro) throws ParseException{
    	Date formatFecha = FormatUtils.stringToDate(fechaCobro);
    	String fechaFin = FormatUtils.formatFecFin(formatFecha);
    	String sql = Query;
    	return (List<ImpReporteCobroDTO>) jdbcTemplate.query(sql, 
        		new Object[] {fechaFin}, 
        		(sp, rowNum) ->
				new ImpReporteCobroDTO(
		 			sp.getInt("NUM_CLIENTE"),
		 			sp.getString("CUENTA_EJE"),
		 			sp.getString("NUM_CTA_RESP"),
	                sp.getDouble("TOTAL"),
	                sp.getString("MES"),
	                sp.getInt("ANIO"),
	                sp.getString("SERVICIO"),
	                sp.getDouble("COM_SIN_IVA"),
	                sp.getDouble("IVAA"),
	                sp.getString("LLAVE"),
	                sp.getString("NUM_PROTECCION"),
	                sp.getString("FEC_COBRO"),
	                sp.getString("FEC_CONTABLE"),
	                sp.getString("CNTR_CLIENTE_USUARIO")
	 			));

    }
    
    private final String Query = "SELECT m.num_cliente, replace(replace(m.cta_cliente,' ','-'),'-----','-') Cuenta_Eje,"
    		+"replace(replace(m.cta_cliente,' ','-'),'-----','-') Num_Cta_resp,"
    		+ "m.total, m.mes, m.anio, m.servicio, m.com_sin_iva, m.ivaa, "
    		+ "m.llave, m.num_proteccion, to_char(m.fec_cobro, 'dd/MM/yyyy') fec_cobro,"
    		+ "to_char(m.fec_cobro + INTERVAL '1' DAY, 'dd/MM/yyyy') fec_contable, m.cntr_cliente_usuario "
    		+ "FROM PPC_PCB_BASEPERFILES m "
    		+ "WHERE to_char(m.fec_cobro,'dd/MM/yyyy') = ?";    
    
}
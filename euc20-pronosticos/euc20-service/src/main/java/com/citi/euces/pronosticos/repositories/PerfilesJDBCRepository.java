package com.citi.euces.pronosticos.repositories;

import java.text.ParseException;
import java.util.Date;
import java.util.List;
import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import com.citi.euces.pronosticos.infra.dto.ImpReporteCobroDTO;
import com.citi.euces.pronosticos.infra.utils.FormatUtils;

@Repository
public class PerfilesJDBCRepository {
	static final Logger log = LoggerFactory.getLogger(PerfilesJDBCRepository.class);
	
	private final JdbcTemplate jdbcTemplate;

    public PerfilesJDBCRepository(JdbcTemplate jdbcTemplate) {
    	this.jdbcTemplate = jdbcTemplate;
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
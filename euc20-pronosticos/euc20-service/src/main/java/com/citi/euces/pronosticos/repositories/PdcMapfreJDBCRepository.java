package com.citi.euces.pronosticos.repositories;

import java.text.ParseException;
import java.util.Date;
import java.util.List;
import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import com.citi.euces.pronosticos.infra.dto.LayoutPrevioDTO;
import com.citi.euces.pronosticos.infra.utils.FormatUtils;
import com.citi.euces.pronosticos.services.PdcMapfreServiceImp;

@Repository
public class PdcMapfreJDBCRepository {
	static final Logger log = LoggerFactory.getLogger(PdcMapfreServiceImp.class);
	
	private final JdbcTemplate jdbcTemplate;

    public PdcMapfreJDBCRepository(JdbcTemplate jdbcTemplate) {
    	this.jdbcTemplate = jdbcTemplate;
	}
    
    @Transactional
    public List<LayoutPrevioDTO> ObtenerConsulta(String fecCarga) throws ParseException {
    	Date formatFecha = FormatUtils.stringToDate(fecCarga);
    	String fechaFin = FormatUtils.formatFecFin(formatFecha);
    	log.info("le Fecha de la consulta es  :: "+ formatFecha);
    	log.info("le Fecha de la consulta es :: "+ fechaFin);
    	String sql = QueryLayoutPrevio;
        return (List<LayoutPrevioDTO>) jdbcTemplate.query(sql, 
        		new Object[] {fechaFin}, 
        		(sp, rowNum) ->
        	 	new LayoutPrevioDTO(
        	 			sp.getString("FEC_CARGA"),
                        sp.getString("CTRATO_TEL_CTA"),
                        sp.getString("CTA_ORIGEN"),
                        sp.getDouble("CANTIDAD"),
                        sp.getString("EMISOR"),
                        sp.getInt("DIAS"),
                        sp.getString("CTA_ABONO"),
                        sp.getString("CONCEPTO"),
                        sp.getString("REF_EMISOR"),
                        sp.getString("LEYENDA")
        	 			));
    }
    
    private final String QueryLayoutPrevio = "SELECT to_char(m.fec_carga, 'dd/MM/yyyy') fec_carga, m.ctrato_tel_cta, m.cta_origen,"
			+"m.cantidad, m.emisor, m.dias, m.cta_abono, m.concepto, m.ref_emisor, m.leyenda "
			+"FROM PPC_PDC_MAPFRE m "
			+"WHERE to_char(m.fec_carga,'dd/MM/yyyy')= ? AND m.cta_origen like '%/%'";
    
    /*
    @Transactional
    public List<LayoutPrevioDTO> findAll() {
        String sql = "SELECT to_char(m.fec_carga, 'DD/MM/YYYY') fec_carga, m.ctrato_tel_cta, m.cta_origen,"
    			+ "m.cantidad, m.emisor, m.dias, m.cta_abono, m.concepto, m.ref_emisor, m.leyenda "
        		+ "FROM PPC_PDC_MAPFRE m ";
        return jdbcTemplate.query(sql, (sp, rowNum) ->
        	new LayoutPrevioDTO(
                sp.getString("FEC_CARGA"),
                sp.getString("CTRATO_TEL_CTA"),
                sp.getString("CTA_ORIGEN"),
                sp.getDouble("CANTIDAD"),
                sp.getString("EMISOR"),
                sp.getInt("DIAS"),
                sp.getString("CTA_ABONO"),
                sp.getString("CONCEPTO"),
                sp.getString("REF_EMISOR"),
                sp.getString("LEYENDA")
                )
        	);
    }
*/
    

}

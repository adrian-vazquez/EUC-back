package com.citi.euces.pronosticos.repositories;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class SubirRespuestaJDBCRepository {
	
static final Logger log = LoggerFactory.getLogger(SubirRespuestaJDBCRepository.class);
	
	private final JdbcTemplate jdbcTemplate;

    public SubirRespuestaJDBCRepository(JdbcTemplate jdbcTemplate) {
    	this.jdbcTemplate = jdbcTemplate;
	}
	
    public void truncateTmpRespuesta() {
        String query = "DELETE FROM PPC_PCB_RESPUESTA";
        jdbcTemplate.execute(query);
    }
    
    /*
    public String updateMaestoComisionesSp(){
        jdbcTemplate.setResultsMapCaseInsensitive(true);
        simpleJdbcCallRefCursor = new SimpleJdbcCall(jdbcTemplate)
                .withProcedureName("PRUEBA_SP")
                .declareParameters(
                        new SqlParameter("p_numRegCargados", Types.INTEGER),
                        new SqlOutParameter("p_numRegCargados", Types.INTEGER));
        Map<String, Object> out = simpleJdbcCallRefCursor.execute(new MapSqlParameterSource("p_numRegCargados", 0));
        log.info("updateMaestoComisionesSp p_numRegCargados :: >> " +  out.get("p_numRegCargados"));
        return out.get("p_numRegCargados").toString();
    }*/

}

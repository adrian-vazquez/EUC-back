package com.citi.euces.pronosticos.repositories;

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

import com.citi.euces.pronosticos.infra.dto.RebajaFileOndemandDTO;
import com.citi.euces.pronosticos.infra.exceptions.GenericException;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Types;
import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

@Repository
public class RebajaPronosticoJDBCRepository {

    private final JdbcTemplate jdbcTemplate;
    private SimpleJdbcCall simpleJdbcCallRefCursor;
    static final Logger log = LoggerFactory.getLogger(RebajaPronosticoJDBCRepository.class);
    
    
    public RebajaPronosticoJDBCRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
    @Transactional
    public void truncateTable() {
        String query = "DELETE FROM PPC_MIS_REBAJA_PRONOSTICO";
        jdbcTemplate.execute(query);
    }

    @Transactional
    public int[][] batchInsert(List<RebajaFileOndemandDTO> books, int batchSize) {
        System.out.println("Rebaja Pronostico :: content :: " + books.size());

        int[][] updateCounts = jdbcTemplate.batchUpdate(
                "INSERT INTO PPC_MIS_REBAJA_PRONOSTICO(NUM_PROTECCION) "
                + "values(?)",
                books,
                batchSize,
                new ParameterizedPreparedStatementSetter<RebajaFileOndemandDTO>() {
                    public void setValues(PreparedStatement ps, RebajaFileOndemandDTO content) throws SQLException {
                    	ps.setLong(1, content.getNumProteccion());
                    }
                });
        return updateCounts;
	}
	
	@Transactional
    public String updateRebajaPronosticosEv() throws GenericException{
		try {
			jdbcTemplate.setResultsMapCaseInsensitive(true);
	        simpleJdbcCallRefCursor = new SimpleJdbcCall(jdbcTemplate)
	                .withProcedureName("PPC_MIS_SP_UPDATE_REBAJA_PRONOSTICOS_EV")
	                .declareParameters(new SqlParameter("p_RegCargados", Types.INTEGER),
	                new SqlOutParameter("p_RegCargados", Types.INTEGER));
	        Map<String, Object> out = simpleJdbcCallRefCursor.execute(new MapSqlParameterSource("p_RegCargados", 0));
	        log.info("updateRebajaPronosticos p_RegCargados :: >> " +  out.get("p_RegCargados"));
	        return out.get("p_RegCargados").toString();	
		}catch (Exception e) {
			e.printStackTrace();
            throw new GenericException( "Error al ejecutar PPC_MIS_SP_UPDATE_REBAJA_PRONOSTICOS_EV :: ", HttpStatus.NOT_FOUND.toString());
        }
    }
}

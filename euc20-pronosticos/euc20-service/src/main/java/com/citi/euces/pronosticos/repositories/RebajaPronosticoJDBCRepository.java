package com.citi.euces.pronosticos.repositories;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ParameterizedPreparedStatementSetter;
import org.springframework.stereotype.Repository;
import com.citi.euces.pronosticos.infra.dto.RebajaFileOndemandDTO;
import com.citi.euces.pronosticos.infra.exceptions.GenericException;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import javax.transaction.Transactional;

@Repository
public class RebajaPronosticoJDBCRepository {

    private final JdbcTemplate jdbcTemplate;
    static final Logger log = LoggerFactory.getLogger(RebajaPronosticoJDBCRepository.class);
    
    
    public RebajaPronosticoJDBCRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
    @Transactional
    public void truncateTable() {
        String query = "DELETE FROM PPC_MIS_REBAJA_PRONOSTICO";
        jdbcTemplate.execute(query);
    }

    public int[][] batchInsert(List<RebajaFileOndemandDTO> books, int batchSize) {
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
    public Integer updateRebajaPronosticosEv() throws GenericException{
		try {
			Integer valor = 0;
			Connection connection = jdbcTemplate.getDataSource().getConnection();
			CallableStatement cstmt =  connection.prepareCall("{call PPC_MIS_SP_UPDATE_REBAJA_PRONOSTICOS_EV(?)}");
			cstmt.registerOutParameter("p_RegCargados", java.sql.Types.INTEGER);
			cstmt.execute();
			valor = cstmt.getInt("p_RegCargados");     
			cstmt.close();
		    return valor;
		}catch (Exception e) {
			e.printStackTrace();
            throw new GenericException( "Error al ejecutar PPC_MIS_SP_UPDATE_REBAJA_PRONOSTICOS_EV :: ", HttpStatus.NOT_FOUND.toString());
        }
    }
}

package com.citi.euces.pronosticos.repositories;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ParameterizedPreparedStatementSetter;
import org.springframework.stereotype.Repository;

import com.citi.euces.pronosticos.infra.dto.RebajaFileOndemandDTO;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import javax.transaction.Transactional;

@Repository
public class RebajaPronosticoJDBCRepository {

    private final JdbcTemplate jdbcTemplate;

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
}

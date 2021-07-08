package com.citi.euces.pronosticos.repositories;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

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
	public void TCBorrarRebajaPronosticos() {
        String query = "Truncate table PPC_MIS_REBAJA_PRONOSTICO";
        jdbcTemplate.execute(query);
    }
}

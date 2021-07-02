package com.citi.euces.pronosticos.repositories;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class RebajaPronosticoJDBCRepository {

    private final JdbcTemplate jdbcTemplate;

    public RebajaPronosticoJDBCRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void truncateTable() {
        String query = "Truncate table PPC_MIS_REBAJA_PRONOSTICO";
        jdbcTemplate.execute(query);
    }

}

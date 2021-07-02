package com.citi.euces.pronosticos.repositories;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class Estatus15JDBCRepository {
    private final JdbcTemplate jdbcTemplate;

    public Estatus15JDBCRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void truncateTableEstatus15() {
        String query = "Truncate table PPC_MIS_ESTATUS15";
        jdbcTemplate.execute(query);
    }

    public void truncateTableEstatus15tmp() {
        String query = "Truncate table PPC_MIS_ESTATUS15_TMP";
        jdbcTemplate.execute(query);
    }
}
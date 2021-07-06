package com.citi.euces.pronosticos.repositories;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
public class Estatus15JDBCRepository {
    private final JdbcTemplate jdbcTemplate;

    public Estatus15JDBCRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Transactional
    public void truncateTableEstatus15() {
        String query = "DELETE FROM PPC_MIS_ESTATUS15";
        jdbcTemplate.execute(query);
    }

    @Transactional
    public void truncateTableEstatus15tmp() {
        String query = "DELETE FROM PPC_MIS_ESTATUS15_TMP";
        jdbcTemplate.execute(query);
    }
}
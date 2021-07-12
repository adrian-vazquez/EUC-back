package com.citi.euces.pronosticos.repositories;

import javax.transaction.Transactional;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class ReintentosJDBCRepository {

	private final JdbcTemplate jdbcTemplate;

	public ReintentosJDBCRepository(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	@Transactional
	public void truncateTableReintentos() {
		String query = "DELETE FROM PPC_MIS_REINTENTOS";
		jdbcTemplate.execute(query);
	}

}

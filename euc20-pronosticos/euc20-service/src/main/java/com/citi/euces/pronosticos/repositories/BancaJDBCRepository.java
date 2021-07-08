package com.citi.euces.pronosticos.repositories;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.citi.euces.pronosticos.infra.dto.BancaDTO;
import com.citi.euces.pronosticos.infra.dto.CuentasContablesDTO;

@Repository
public class BancaJDBCRepository {

	private final JdbcTemplate jdbcTemplate;

	public BancaJDBCRepository(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
	
	@Transactional
	public List<BancaDTO> selectAll()
	{
		String sql = "SELECT * FROM PPC_MIS_BANCA";
        return jdbcTemplate.query(sql, (cc, rowNum) ->
                new BancaDTO(
                        cc.getInt("NO_CLIENTE"),
                        cc.getInt("BANCA")
                )
        );
	}
}

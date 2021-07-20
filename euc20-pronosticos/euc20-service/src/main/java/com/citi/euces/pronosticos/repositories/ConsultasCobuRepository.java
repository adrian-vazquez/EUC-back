package com.citi.euces.pronosticos.repositories;

import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.citi.euces.pronosticos.infra.dto.CifrasControlDTO;

@Repository
public class ConsultasCobuRepository {
	
	private final JdbcTemplate consultas;

    public ConsultasCobuRepository(JdbcTemplate consultas) {
    	this.consultas = consultas;
	}
    
    @Transactional
	public List<CifrasControlDTO>cosultaCifras(){
		String consulta = "SELECT * FROM PPC_PCB_CIFRAS_CONTROL";
		
		return consultas.query(
				consulta, new Object[] {},
				(sp, rowNum) ->
				new CifrasControlDTO(
						sp.getString("CONSULTA"),
						sp.getInt("CIFRA")
						));
	}

}

package com.citi.euces.pronosticos.repositories;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ParameterizedPreparedStatementSetter;
import org.springframework.stereotype.Repository;

import com.citi.euces.pronosticos.infra.dto.CtasVirtualesDTO;


@Repository
public class InsertsCobuRepository {
	
	 private final JdbcTemplate inserts;

	    public InsertsCobuRepository(JdbcTemplate inserts) {
	    this.inserts = inserts;
	    }
	    
	    @Transactional
	    public int insertCtasVirtuales(List<CtasVirtualesDTO> contenido) {
	        int updateCounts = inserts.update(
	        		"INSERT INTO PPC_PCB_CTAS_VIRTUALES( NUM_CLIENTE, NUM_CUENTA, FEC_ALTA, CUENTAS_X, NOMBRE ) VALUES(?,?,?,?,?)",
	        		
	                new ParameterizedPreparedStatementSetter<CtasVirtualesDTO>() {
	                    public void setValues(PreparedStatement ps, CtasVirtualesDTO argument) throws SQLException {
	                    	
	                        ps.setLong(1, argument.getNumCliente());
	                        ps.setLong(2, argument.getNumCuenta());
	                        ps.setString(3, argument.getFecAlta());
	                        ps.setLong(4, argument.getCuentasX());
	                        ps.setString(5, argument.getNombre());
	                    }
	                });
	        return updateCounts;
	    }
	    
}

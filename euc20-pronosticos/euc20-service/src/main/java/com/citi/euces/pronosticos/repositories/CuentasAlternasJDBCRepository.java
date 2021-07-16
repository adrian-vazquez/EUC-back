package com.citi.euces.pronosticos.repositories;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ParameterizedPreparedStatementSetter;
import org.springframework.stereotype.Repository;

import com.citi.euces.pronosticos.infra.dto.CuentasAlternasDTO;

@Repository
public class CuentasAlternasJDBCRepository {
	
	private final JdbcTemplate jdbcTemplate;

	
	public CuentasAlternasJDBCRepository(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
	
	@Transactional
    public void TruncateTbCuentasAlternas() {
        String query = "DELETE FROM PPC_MIS_CUENTAS_ALTERNAS_TMP";
        jdbcTemplate.execute(query);
    }
	
	@Transactional
    public int[][] batchInsert(List<CuentasAlternasDTO> books, int batchSize) {
        System.out.println("repository :: content :: " + books.size());

        int[][] updateCounts = jdbcTemplate.batchUpdate(
                "INSERT INTO PPC_MIS_CUENTAS_ALTERNAS_TMP( NUM_CLIENTE, NUM_PRODUCTO, CVE_INSTRUMENTO, NUM_CONTRATO, CVE_ESTATUS, SDO_ACTUAL, PREFMDA, CUENTAMDA) "
                + "values(?,?,?,?,?,?,?,?)",
                books,
                batchSize,
                new ParameterizedPreparedStatementSetter<CuentasAlternasDTO>() {
                    public void setValues(PreparedStatement ps, CuentasAlternasDTO content) throws SQLException {
                    	ps.setLong(1, content.getNum_Cliente());
                    	ps.setLong(2, content.getNum_Producto());
                    	ps.setLong(3, content.getCve_Instrumento());
                    	ps.setLong(4, content.getNum_Contrato());
                    	ps.setLong(5, content.getCve_Estatus());
                    	ps.setDouble(6, content.getSdo_Actual());
                    	ps.setLong(7, content.getPrefmda());
                    	ps.setLong(8, content.getCuentamda());
                    }
                });
        return updateCounts;
	}
}

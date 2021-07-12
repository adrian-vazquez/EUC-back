package com.citi.euces.pronosticos.repositories;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ParameterizedPreparedStatementSetter;
import org.springframework.stereotype.Repository;

import com.citi.euces.pronosticos.infra.dto.CtasVirtualesDTO;
import com.citi.euces.pronosticos.infra.dto.ProcesadoDTO;
import com.citi.euces.pronosticos.infra.dto.QueryCtosAgrupadoDTO;
import com.citi.euces.pronosticos.infra.dto.TxsCtasVirtDTO;


@Repository
public class InsertsCobuRepository {
	
	 private final JdbcTemplate inserts;

	    public InsertsCobuRepository(JdbcTemplate inserts) {
	    this.inserts = inserts;
	    }
	    
	    @Transactional
	    public int[][] insertCtasVirtuales(List<CtasVirtualesDTO> books, int batchSize) {
	        int [][]updateCounts = inserts.batchUpdate(
	        		"INSERT INTO PPC_PCB_CTAS_VIRTUALES( NUM_CLIENTE, NUM_CUENTA, FEC_ALTA, CUENTAS_X, NOMBRE) VALUES(?,?,?,?,?)",
	        		books,
	                batchSize,
	                new ParameterizedPreparedStatementSetter<CtasVirtualesDTO>() {
	                    public void setValues(PreparedStatement ps, CtasVirtualesDTO argument) throws SQLException {
	                    	
	                        ps.setInt(1, argument.getNumCliente());
	                        ps.setInt(2, argument.getNumCuenta());
	                        ps.setString(3, argument.getFecAlta());
	                        ps.setInt(4, argument.getCuentasX());
	                        ps.setString(5, argument.getNombre());
	                        ps.setInt(6, argument.getId());
	                        
	                    }
	                });
	        return updateCounts;
	    }
	    
	    @Transactional
	    public int[][] insertCtasCobu(List<QueryCtosAgrupadoDTO> books, int batchSize) {
	        int [][]updateCounts = inserts.batchUpdate(
	        		"INSERT INTO PPC_PCB_QUERY_CTOS_AGRUPADO(CUENTA, PREFMDA, CUENTAMDA, CVE_ESTATUS, NOMBRE, USO, MON, FRANQUICIA, DUPLICADO, ID) VALUES(?,?,?,?,?,?,?,?,?,?)",
	        		books,
	                batchSize,
	                new ParameterizedPreparedStatementSetter<QueryCtosAgrupadoDTO>() {
	                    public void setValues(PreparedStatement ps, QueryCtosAgrupadoDTO argument) throws SQLException {
	                    	
	                        ps.setLong(1, argument.getCuenta());
	                        ps.setInt(2, argument.getPrefmda());
	                        ps.setInt(3, argument.getCuentamda());
	                        ps.setInt(4, argument.getCveEstatus());
	                        ps.setString(5, argument.getNombre());
	                        ps.setInt(6, argument.getUso());
	                        ps.setInt(7, argument.getMon());
	                        ps.setInt(8, argument.getFranquicia());
	                        ps.setInt(9, argument.getDuplicado());
	                        ps.setInt(10, argument.getId());
	                        
	                    }
	                });
	        return updateCounts;
	    }
	    
	    @Transactional
	    public int[][] insertTxsCtas(List<TxsCtasVirtDTO> books, int batchSize) {
	        int [][]updateCounts = inserts.batchUpdate(
	        		"INSERT INTO PPC_PCB_TXS_CTAS_VIRT(NUM_CLIENTE, NUM_CTA, CTE_ALIAS, NOMBRE, CVE_MON_SISTEMA, FEC_INFORMACION, NUM_MED_ACCESO, CVE_TXNSISTEMA, NUM_SUC_PROMTORMDA, IMP_TRANSACCION, NUM_AUT_TRANS, NUM_SUC_OPERADORA, TIPO) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?)",
	        		books,
	                batchSize,
	                new ParameterizedPreparedStatementSetter<TxsCtasVirtDTO>() {
	                    public void setValues(PreparedStatement ps, TxsCtasVirtDTO argument) throws SQLException {
	                    	
	                        ps.setLong(1, argument.getNumCliente());
	                        ps.setLong(2, argument.getNumCta());
	                        ps.setString(3, argument.getCteAlias());
	                        ps.setString(4, argument.getNombre());
	                        ps.setInt(5, argument.getCveMonSstema());
	                        ps.setDate(6, (Date) argument.getFecInformacion());
	                        ps.setDouble(7, argument.getNumMedAcceso());
	                        ps.setInt(8, argument.getCveTxnSistema());
	                        ps.setInt(9, argument.getNumSucPromtormda());
	                        ps.setDouble(10, argument.getImpTransaccion());
	                        ps.setDouble(11, argument.getNumAutTrans());
	                        ps.setInt(12, argument.getNumSucOperadora());
	                        ps.setInt(13, argument.getTipo());
	                        ps.setInt(14, argument.getId());
	                        
	                    }
	                });
	        return updateCounts;
	    }
	    
	    @Transactional
	    public int[][] insertTarEspCobu(List<ProcesadoDTO> books, int batchSize) {
	        int [][]updateCounts = inserts.batchUpdate(
	        		"INSERT INTO PPC_PCB_PROCESADO( NO_CLIENTE, BE, VENTANILLA, MENSUALIDAD, ID) VALUES(?,?,?,?,?)",
	        		books,
	                batchSize,
	                new ParameterizedPreparedStatementSetter<ProcesadoDTO>() {
	                    public void setValues(PreparedStatement ps, ProcesadoDTO argument) throws SQLException {
	                    	
	                        ps.setLong(1, argument.getNumCielnte());
	                        ps.setDouble(2, argument.getBe());
	                        ps.setDouble(3, argument.getVentanilla());
	                        ps.setDouble(4, argument.getMensualidad());
	                        ps.setInt(5, argument.getId());
	                        
	                    }
	                });
	        return updateCounts;
	    }
}

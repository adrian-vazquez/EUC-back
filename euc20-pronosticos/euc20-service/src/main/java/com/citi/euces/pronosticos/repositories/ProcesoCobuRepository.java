package com.citi.euces.pronosticos.repositories;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ParameterizedPreparedStatementSetter;
import org.springframework.stereotype.Repository;

import com.citi.euces.pronosticos.infra.dto.CifrasControlDTO;

@Repository
public class ProcesoCobuRepository{

	 private final JdbcTemplate procesos;

	    public ProcesoCobuRepository(JdbcTemplate procesos) {
	    this.procesos = procesos;
	    }
	    
	    public int insertaTarifas() {
	    	int resultado = 0;
	    	return resultado;
	    }
	    
	    public int insertaQueryCtosDuplicado() {
	    	int resultado = 0;
	    	return resultado;
	    }
	    
	    public int actualizaQueryCtosDuplicados() {
	    	int resultado = 0;
	    	return resultado;
	    }
	    
	    public int insertaCtosUnicos() {
	    	int resultado = 0;
	    	return resultado;
	    }
	    
	    public int actualizaCtasVirtalesMesyAnio() {
	    	int resultado = 0;
	    	return resultado;
	    }
	   
	    public int insertaCtasVirtualesGpos() {
	    	int resultado = 0;
	    	return resultado;
	    }
	    
	    public int actualizaTxsCtasVirtNunSucOperadora() {
	    	int resultado = 0;
	    	return resultado;
	    }
	    
	    public int insertaTxnsXTipo() {
	    	int resultado = 0;
	    	return resultado;
	    }
	    
	    public int actualizaCtasVirtualesGposTxnsBe() {
	    	int resultado = 0;
	    	return resultado;
	    }
	    
	    public int actualizaCtasVirtualesGposTxnsVent() {
	    	int resultado = 0;
	    	return resultado;
	    }
	    
	    public int actualizaANull() {
	    	int resultado = 0;
	    	return resultado;
	    }
	    
	    public int actualizaCtasVirtualesGposconTablaTarifas() {
	    	int resultado = 0;
	    	return resultado;
	    }
	    
	    public int actualizaAUnaTarifaPredefinida() {
	    	int resultado = 0;
	    	return resultado;
	    }
	    
	    public int actualizaCtasVirtualesGposBeVentMens(){
	    	int resultado = 0;
	    	return resultado;
	    }
	    
	    public int actualizaCtosUnicos() {
	    	int resultado = 0;
	    	return resultado;
	    }
	    
	    public int actualizaCtosUnicosUso11() {
	    	int resultado = 0;
	    	return resultado;
	    }
	    
	    public int actualizaCtasVirtualesGposComTotal() {
	    	int resultado = 0;
	    	return resultado;
	    }
	    
	    public int actualizaCtasVirtualesGposIva() {
	    	int resultado = 0;
	    	return resultado;
	    }
	    
	    public int insertaEnLayoutBe() {
	    	int resultado = 0;
	    	return resultado;
	    }
	    
	    public int insertaEnLayoutVent() {
	    	int resultado = 0;
	    	return resultado;
	    }
	    
	    public int insertaEnLayoutMens() {
	    	int resultado = 0;
	    	return resultado;
	    }
	    
	    public int insertaEnLayoutTxnsConImporte() {
	    	int resultado = 0;
	    	return resultado;
	    }
	    
	    @Transactional
	    public int[][] insertCifrasControl(List<CifrasControlDTO> books, int batchSize) {
	        int [][]updateCounts = procesos.batchUpdate(
	        		"INSERT INTO PPC_PCB_CIFRAS_CONTROL(CONSULTA, CIFRA, PROCESO, ID) VALUES(?,?,?,?)",
	        		books,
	                batchSize,
	                new ParameterizedPreparedStatementSetter<CifrasControlDTO>() {
	                    public void setValues(PreparedStatement ps, CifrasControlDTO argument) throws SQLException {
	                    	
	                        ps.setString(1, argument.getConsulta().toString());
	                        ps.setString(2, argument.getCifra().toString());
	                        ps.setString(3, argument.getProceso());
	                        ps.setInt(4, argument.getId());
	                        
	                    }
	                });
	        return updateCounts;
	    }
}

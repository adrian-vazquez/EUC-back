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
	   
		public int insertaTarifas() throws SQLException{  
			int contador = 0;
			String query = "INSERT INTO PPC_PCB_TARIFAS(NUM_CLIENTE, TARIFA_TX_BE, TARIFA_TX_SUC, TARIFA_MENSUAL, ID)SELECT NO_CLIENTE, BE, VENTANILLA, MENSUALIDAD, ID FROM PPC_PCB_PROCESADO";
			contador = procesos.update(query);
	    	return contador;
	    	
	    } 
	   

		public int insertaQueryCtosDuplicado() {
	    	int transacciones = 0;
	    	return transacciones;
	    }
	    
	    public int actualizaQueryCtosDuplicados() {
	    	int transacciones = 2;
	    	return transacciones;
	    }
	    
	    public int insertaCtosUnicos() {
	    	int transacciones = 3;
	    	return transacciones;
	    }
	    
	    public int actualizaCtasVirtalesMesyAnio() {
	    	int transacciones = 4;
	    	return transacciones;
	    }
	   
	    public int insertaCtasVirtualesGpos() {
	    	int transacciones = 5;
	    	return transacciones;
	    }
	    
	    public int actualizaTxsCtasVirtNunSucOperadora() {
	    	int transacciones = 6;
	    	return transacciones;
	    }
	    
	    public int insertaTxnsXTipo() {
	    	int transacciones = 7;
	    	return transacciones;
	    }
	    
	    public int actualizaCtasVirtualesGposTxnsBe() {
	    	int transacciones = 8;
	    	return transacciones;
	    }
	    
	    public int actualizaCtasVirtualesGposTxnsVent() {
	    	int transacciones = 9;
	    	return transacciones;
	    }
	    
	    public int actualizaANull() {
	    	int transacciones = 10;
	    	return transacciones;
	    }
	    
	    public int actualizaCtasVirtualesGposconTablaTarifas() {
	    	int transacciones = 11;
	    	return transacciones;
	    }
	    
	    public int actualizaAUnaTarifaPredefinida() {
	    	int transacciones = 12;
	    	return transacciones;
	    }
	    
	    public int actualizaCtasVirtualesGposBeVentMens(){
	    	int transacciones = 13;
	    	return transacciones;
	    }
	    
	    public int actualizaCtosUnicos() {
	    	int transacciones = 14;
	    	return transacciones;
	    }
	    
	    public int actualizaCtosUnicosUso11() {
	    	int transacciones = 15;
	    	return transacciones;
	    }
	    
	    public int actualizaCtasVirtualesGposComTotal() {
	    	int transacciones = 16;
	    	return transacciones;
	    }
	    
	    public int actualizaCtasVirtualesGposIva() {
	    	int transacciones = 17;
	    	return transacciones;
	    }
	    
	    public int insertaEnLayoutBe() {
	    	int transacciones = 18;
	    	return transacciones;
	    }
	    
	    public int insertaEnLayoutVent() {
	    	int transacciones = 19;
	    	return transacciones;
	    }
	    
	    public int insertaEnLayoutMens() {
	    	int transacciones = 20;
	    	return transacciones;
	    }
	    
	    public int insertaEnLayoutTxnsConImporte() {
	    	int transacciones = 21;
	    	return transacciones;
	    }
	    
	    /************************************************************************************************/
		/************************************************************************************************/
	    
	    @Transactional
	    public int[][] insertCifrasControl(List<CifrasControlDTO> books, int batchSize) {
	        int [][]updateCounts = procesos.batchUpdate(
	        		"INSERT INTO PPC_PCB_CIFRAS_CONTROL(CONSULTA, CIFRA, PROCESO, ID) VALUES(?,?,?,?)",
	        		books,
	                batchSize,
	                new ParameterizedPreparedStatementSetter<CifrasControlDTO>() {
	                    public void setValues(PreparedStatement ps, CifrasControlDTO argument) throws SQLException {
	                    	
	                        ps.setString(1, argument.getConsulta().toString());
	                        ps.setInt(2, argument.getCifra());
	                        ps.setString(3, argument.getProceso());
	                        ps.setInt(4, argument.getId());
	                        
	                    }
	                });
	        return updateCounts;
	    }
}

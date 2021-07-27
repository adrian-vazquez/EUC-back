package com.citi.euces.pronosticos.repositories;

import java.sql.SQLException;
import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.citi.euces.pronosticos.infra.dto.CifrasControlDTO;
import com.citi.euces.pronosticos.infra.dto.CuentasVirtulesGposDTO;
import com.citi.euces.pronosticos.infra.dto.LayoutBeDTO;
import com.citi.euces.pronosticos.infra.dto.LayoutMensDTO;
import com.citi.euces.pronosticos.infra.dto.LayoutVentDTO;
import com.citi.euces.pronosticos.infra.dto.TarifasDTO;
import com.citi.euces.pronosticos.infra.dto.TxnsImporteDTO;

@Repository
public class ConsultasCobuRepository {
	
	private final JdbcTemplate consultas;

    public ConsultasCobuRepository(JdbcTemplate consultas) {
    	this.consultas = consultas;
	}
    
    public int countLayoutBe() throws SQLException{  
		String query = "SELECT COUNT(1) FROM ( "
				+ "SELECT tp.CTA, COUNT(1) "
				+ "FROM PPC_PCB_LAYOUT_BE tp "
				+ "GROUP BY tp.CTA "
				+ "HAVING COUNT(1) > 1)";
		int count= consultas.queryForObject(query, Integer.class);
    	return count;		
    } 
    public int countLayouMens() throws SQLException{  
		String query = "SELECT COUNT(1) FROM ( "
				+ "SELECT tp.CTA, COUNT(1) "
				+ "FROM PPC_PCB_LAYOUT_MENS tp "
				+ "GROUP BY tp.CTA "
				+ "HAVING COUNT(1) > 1)";
		int count= consultas.queryForObject(query, Integer.class);
    	return count;	
    } 
    public int countLayoutVent() throws SQLException{  
		String query = "SELECT COUNT(1) FROM ( "
				+ "SELECT tp.CTA, COUNT(1) "
				+ "FROM PPC_PCB_LAYOUT_VENT tp "
				+ "GROUP BY tp.CTA "
				+ "HAVING COUNT(1) > 1)";
		int count= consultas.queryForObject(query, Integer.class);
    	return count;		
    } 
    
    //*****************************************************************//
    
    @Transactional
	public List<LayoutBeDTO>cosultaLayoutBe(){
		String consulta = "SELECT * FROM PPC_PCB_LAYOUT_BE";
		
		return consultas.query(
				consulta, new Object[] {},
				(sp, rowNum) ->
				new LayoutBeDTO(
						sp.getLong("NUM_CLIENTE"),
						sp.getString("NOMBRE"),
						sp.getInt("SUC"),
						sp.getLong("CTA"),
						sp.getDouble("COM_BE"),
						sp.getDouble("MONTO_IVA"),
						sp.getFloat("MONTO_TOTAL"),
						sp.getInt("ANIO"),
						sp.getInt("MES"),
						sp.getString("PRODUCTO"),
						sp.getDouble("IVA"),
						sp.getDouble("MONEDA"),
						sp.getString("USO_11")
						));
	}
    
    @Transactional
   	public List<LayoutMensDTO>cosultaLayoutMens(){
   		String consulta = "SELECT * FROM PPC_PCB_LAYOUT_MENS";
   		
   		return consultas.query(
   				consulta, new Object[] {},
   				(sp, rowNum) ->
   				new LayoutMensDTO(
   						sp.getLong("NUM_CLIENTE"),
   						sp.getString("NOMBRE"),
   						sp.getInt("SUC"),
   						sp.getLong("CTA"),
   						sp.getDouble("COM_MENS"),
   						sp.getDouble("MONTO_IVA"),
   						sp.getDouble("MONTO_TOTAL"),
   						sp.getInt("ANIO"),
   						sp.getInt("MES"),
   						sp.getString("PRODUCTO"),
   						sp.getDouble("IVA"),
   						sp.getDouble("MONEDA")
   						));
   	}
    
    @Transactional
	public List<LayoutVentDTO>cosultaLayoutVent(){
		String consulta = "SELECT * FROM PPC_PCB_LAYOUT_VENT";
		
		return consultas.query(
				consulta, new Object[] {},
				(sp, rowNum) ->
				new LayoutVentDTO(
						sp.getLong("NUM_CLIENTE"),
						sp.getString("NOMBRE"),
						sp.getInt("SUC"),
						sp.getLong("CTA"),
						sp.getDouble("COM_VENT"),
						sp.getDouble("MONTO_IVA"),
						sp.getDouble("MONTO_TOTAL"),
						sp.getInt("ANIO"),
						sp.getInt("MES"),
						sp.getString("PRODUCTO"),
						sp.getDouble("IVA"),
						sp.getDouble("MONEDA"),
						sp.getString("USO_11")
						));
	}
    
    @Transactional
	public List<TarifasDTO>cosultaTarifas(){
		String consulta = "SELECT * FROM PPC_PCB_TARIFAS";
		
		return consultas.query(
				consulta, new Object[] {},
				(sp, rowNum) ->
				new TarifasDTO(
						sp.getLong("NUM_CLIENTE"),
						sp.getDouble("TARIFA_TX_BE"),
						sp.getDouble("TARIFA_TX_SUC"),
						sp.getDouble("TARIFA_MENSUAL")
						));
	}
    
    @Transactional
	public List<CuentasVirtulesGposDTO>cosultaCtasVirtualesGpos(){
		String consulta = "SELECT * FROM PPC_PCB_CTAS_VIRTUALES_GPOS";
		
		return consultas.query(
				consulta, new Object[] {},
				(sp, rowNum) ->
				new CuentasVirtulesGposDTO(
						sp.getLong("NUM_CLIENTE"),
						sp.getLong("NUM_CUENTA"),
						sp.getString("NOMBRE"),
						sp.getLong("CTAS_V"),
						sp.getDouble("TXNS_BE"),
						sp.getDouble("TXNS_VENT"),
						sp.getDouble("TARIFA_BE"),
						sp.getDouble("TARIFA_VENT"),
						sp.getDouble("TARIFA_MENS"),
						sp.getDouble("COM_BE"),
						sp.getDouble("COM_VENT"),
						sp.getDouble("COM_MENS"),
						sp.getString("USO_11"),
						sp.getDouble("COM_TOTAL"),
						sp.getInt("SUC"),
						sp.getLong("CUENTA"),
						sp.getLong("FRANQUICIA"),
						sp.getInt("MONEDA"),
						sp.getDouble("IVA")
						));
	}
    
    @Transactional
   	public List<TxnsImporteDTO>cosultaTxnsImporte(){
   		String consulta = "SELECT * FROM PPC_PCB_TXNS_IMPORTE";
   		
   		return consultas.query(
   				consulta, new Object[] {},
   				(sp, rowNum) ->
   				new TxnsImporteDTO(
   						sp.getLong("NUM_CLIENTE"),
   						sp.getLong("NUM_CUENTA"),
   						sp.getString("TIPO"),
   						sp.getDouble("SUM_IMP_TRANS")
   						));
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

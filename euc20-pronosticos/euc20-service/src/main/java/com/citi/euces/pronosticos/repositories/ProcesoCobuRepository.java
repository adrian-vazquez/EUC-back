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
			String query = "INSERT INTO PPC_PCB_TARIFAS(NUM_CLIENTE, TARIFA_TX_BE, TARIFA_TX_SUC, TARIFA_MENSUAL, ID) "
					+ "SELECT NO_CLIENTE, BE, VENTANILLA, MENSUALIDAD, ID FROM PPC_PCB_PROCESADO";
			contador = procesos.update(query);
	    	return contador;
	    	
	    } 
	   

		public int insertaQueryCtosDuplicado() {
	    	int contador = 1;
	    	String query = "INSERT INTO PPC_PCB_QUERY_CTOS_DUPLICADOS(CUENTA, CTA_CUENTA,ID) "
	    			+ "(SELECT CUENTA, Count(CUENTA) AS CTA_CUENTA, ID "
	    			+ "FROM PPC_PCB_QUERY_CTOS_AGRUPADO "
	    			+ "GROUP BY CUENTA,ID "
	    			+ "HAVING (((Count(PPC_PCB_QUERY_CTOS_AGRUPADO.CUENTA))>1)))";
	    	contador = procesos.update(query);
	    	return contador;
	    }
	    
	    public int actualizaQueryCtosDuplicados() {
	    	int contador = 2;
	    	String query = "";
	    	//contador = procesos.update(query);
	    	return contador;
	    }
	    
	    public int insertaCtosUnicos() {
	    	int contador = 3;
	    	String query = "INSERT INTO PPC_PCB_CTOS_UNICOS( NUM_CUENTA, SUC, CTA,USO, MON, FRANQUICIA, ID) SELECT CUENTA, PREFMDA, CUENTAMDA, USO, MON, FRANQUICIA,ID FROM PPC_PCB_QUERY_CTOS_AGRUPADO";
	    	contador = procesos.update(query);
	    	return contador;
	    }
	    
	    public int actualizaCtasVirtalesMesyAnio() {
	    	int contador = 4;
	    	String query = "";
	    	//contador = procesos.update(query);
	    	return contador;
	    }
	   
	    public int insertaCtasVirtualesGpos() {
	    	int contador = 5;
	    	String query = "INSERT INTO PPC_PCB_CTAS_VIRTUALES_GPOS(NUM_CLIENTE, NUM_CUENTA, NOMBRE, CTAS_V, TXNS_BE, TXNS_VENT, "
	    			+ "TARIFA_BE,TARIFA_VENT,TARIFA_MENS, COM_BE,COM_VENT,COM_MENS, USO_11,COM_TOTAL, SUC, CUENTA, FRANQUICIA, MONEDA, IVA, ID) "
	    			+ "SELECT NUM_CLIENTE, NUM_CUENTA, NOMBRE, SUM(PPC_PCB_CTAS_VIRTUALES.CUENTAS_X) AS CTAS_V, 0 AS TXNS_BE, 0 AS TXNS_VENT, "
	    			+ "0 AS TARIFA_BE,  0 AS TARIFA_VENT, 0 AS TARIFA_MENS, 0 AS COM_BE, 0 AS COM_VENT, 0 AS COM_MENS, NULL AS USO_11, "
	    			+ "0 AS COM_TOTAL, 0 AS SUC, 0 AS CTA, 0 AS FRANQUICIA, 0 AS MONEDA, 0 AS IVA, ID "
	    			+ "FROM PPC_PCB_CTAS_VIRTUALES "
	    			+ "GROUP BY NUM_CLIENTE, NUM_CUENTA, NOMBRE, 0, ID, NULL";
	    	contador = procesos.update(query);
	    	return contador;
	    }
	    
	    public int actualizaTxsCtasVirtNunSucOperadora() {
	    	int contador = 6;
	    	String query = "UPDATE PPC_PCB_TXS_CTAS_VIRT "
	    			+ "SET TIPO = "
	    			+ "case NUM_SUC_OPERADORA "
	    			+ "when 870 then 'BE' "
	    			+ "when 519 then 'BE' "
	    			+ "when 859 then 'BE' "
	    			+ "else 'VENT' "
	    			+ "end;";
	    	contador = procesos.update(query);
	    	return contador;
	    }
	    
	    public int insertaTxnsXTipo() {
	    	int contador = 7;
	    	String query = "UPDATE PPC_PCB_CTAS_VIRTUALES_GPOS "
	    			+ "SET PPC_PCB_CTAS_VIRTUALES_GPOS.TARIFA_BE = Null, "
	    			+ "PPC_PCB_CTAS_VIRTUALES_GPOS.TARIFA_VENT = Null, "
	    			+ "PPC_PCB_CTAS_VIRTUALES_GPOS.TARIFA_MENS = Null;";
	    	contador = procesos.update(query);
	    	return contador;
	    }
	    
	    public int actualizaCtasVirtualesGposTxnsBe() {
	    	int contador = 8;
	    	String query = "";
	    	contador = procesos.update(query);
	    	return contador;
	    }
	    
	    public int actualizaCtasVirtualesGposTxnsVent() {
	    	int contador = 9;
	    	String query = "";
	    	//contador = procesos.update(query);
	    	return contador;
	    }
	    
	    public int actualizaANull() {
	    	int contador = 10;
	    	String query = "UPDATE PPC_PCB_CTAS_VIRTUALES_GPOS "
	    			+ "SET PPC_PCB_CTAS_VIRTUALES_GPOS.TARIFA_BE = Null, "
	    			+ "PPC_PCB_CTAS_VIRTUALES_GPOS.TARIFA_VENT = Null, "
	    			+ "PPC_PCB_CTAS_VIRTUALES_GPOS.TARIFA_MENS = Null";
	    	contador = procesos.update(query);
	    	return contador;
	    }
	    
	    public int actualizaCtasVirtualesGposconTablaTarifas() {
	    	int contador = 11;
	    	String query = "";
	    	//contador = procesos.update(query);
	    	return contador;
	    }
	    
	    public int actualizaAUnaTarifaPredefinida() {
	    	int contador = 12;
	    	String query = "UPDATE PPC_PCB_CTAS_VIRTUALES_GPOS "
	    			+ "SET PPC_PCB_CTAS_VIRTUALES_GPOS.TARIFA_BE = 4, PPC_PCB_CTAS_VIRTUALES_GPOS.TARIFA_VENT = 14, PPC_PCB_CTAS_VIRTUALES_GPOS.TARIFA_MENS = 10 "
	    			+ "WHERE (((PPC_PCB_CTAS_VIRTUALES_GPOS.TARIFA_BE) is null) "
	    			+ "AND ((PPC_PCB_CTAS_VIRTUALES_GPOS.TARIFA_VENT) is null) "
	    			+ "AND ((PPC_PCB_CTAS_VIRTUALES_GPOS.TARIFA_MENS) is null))";
	    	contador = procesos.update(query);
	    	return contador;
	    }
	    
	    public int actualizaCtasVirtualesGposBeVentMens(){
	    	int contador = 13;
	    	String query = "UPDATE PPC_PCB_CTAS_VIRTUALES_GPOS "
	    			+ "SET PPC_PCB_CTAS_VIRTUALES_GPOS.COM_BE = PPC_PCB_CTAS_VIRTUALES_GPOS.TXNS_BE*PPC_PCB_CTAS_VIRTUALES_GPOS.TARIFA_BE, "
	    			+ "PPC_PCB_CTAS_VIRTUALES_GPOS.COM_VENT = PPC_PCB_CTAS_VIRTUALES_GPOS.TXNS_VENT*PPC_PCB_CTAS_VIRTUALES_GPOS.TARIFA_VENT, "
	    			+ "PPC_PCB_CTAS_VIRTUALES_GPOS.COM_MENS = PPC_PCB_CTAS_VIRTUALES_GPOS.CTAS_V*PPC_PCB_CTAS_VIRTUALES_GPOS.TARIFA_MENS";
	    	contador = procesos.update(query);
	    	return contador;
	    }
	    
	    public int actualizaCtosUnicos() {
	    	int contador = 14;
	    	String query = "";
	    	//contador = procesos.update(query);
	    	return contador;
	    }
	    
	    public int actualizaCtosUnicosUso11() {
	    	int contador = 0;
	    	String query = "";
	    	//procesos.update(query);
	    	return contador;
	    }
	    
	    public int actualizaCtasVirtualesGposComTotal() {
	    	int contador = 16;
	    	String query = "UPDATE PPC_PCB_CTAS_VIRTUALES_GPOS SET PPC_PCB_CTAS_VIRTUALES_GPOS.COM_TOTAL = "
	    			+ "case PPC_PCB_CTAS_VIRTUALES_GPOS.USO_11 "
	    			+ "when 'USO_11' then PPC_PCB_CTAS_VIRTUALES_GPOS.COM_MENS "
	    			+ "else PPC_PCB_CTAS_VIRTUALES_GPOS.COM_BE + PPC_PCB_CTAS_VIRTUALES_GPOS.COM_VENT + PPC_PCB_CTAS_VIRTUALES_GPOS.COM_MENS "
	    			+ "end";
	    	contador = procesos.update(query);
	    	return contador;
	    }
	    
	    public int actualizaCtasVirtualesGposIva() {
	    	int contador = 17;
	    	String query = "UPDATE PPC_PCB_CTAS_VIRTUALES_GPOS SET PPC_PCB_CTAS_VIRTUALES_GPOS.IVA = 16 "
	    			+ "WHERE (((PPC_PCB_CTAS_VIRTUALES_GPOS.IVA)=0))";
	    	contador = procesos.update(query);
	    	return contador;
	    }
	    
	    public int insertaEnLayoutBe() {
	    	int contador = 18;
	    	String query = "";
	    	//contador = procesos.update(query);
	    	return contador;
	    }
	    
	    public int insertaEnLayoutVent() {
	    	int contador = 19;
	    	String query = "";
	    	//contador = procesos.update(query);
	    	return contador;
	    }
	    
	    public int insertaEnLayoutMens() {
	    	int contador = 20;
	    	String query = "";
	    	//contador = procesos.update(query);
	    	return contador;
	    }
	    
	    public int insertaEnLayoutTxnsConImporte() {
	    	int contador = 21;
	    	String query = "INSERT INTO PPC_PCB_TXNS_IMPORTE(NUM_CLIENTE,NUM_CUENTA,TIPO,TXNS,SUM_IMP_TRANS,ID) "
	    			+ "SELECT NUM_CLIENTE,NUM_CTA,TIPO, Count(NUM_CLIENTE) AS TXNS, Sum(IMP_TRANSACCION) AS SUM_IMP_TRANS,ID "
	    			+ "FROM PPC_PCB_TXS_CTAS_VIRT "
	    			+ "GROUP BY NUM_CLIENTE, NUM_CTA, TIPO, ID";
	    	contador = procesos.update(query);
	    	return contador;
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

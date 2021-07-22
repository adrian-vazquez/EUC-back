package com.citi.euces.pronosticos.repositories;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ParameterizedPreparedStatementSetter;
import org.springframework.stereotype.Repository;

import com.citi.euces.pronosticos.infra.dto.CifrasControlDTO;
import com.citi.euces.pronosticos.infra.exceptions.GenericException;

@Repository
public class ProcesoCobuRepository{

	 private final JdbcTemplate procesos;
	

	    public ProcesoCobuRepository(JdbcTemplate procesos) {
	    this.procesos = procesos;
	    }
	   
		public int insertaTarifas() throws SQLException, GenericException{  
			int contador = 0;
			String query = "INSERT INTO PPC_PCB_TARIFAS(NUM_CLIENTE, TARIFA_TX_BE, TARIFA_TX_SUC, TARIFA_MENSUAL, ID) "
					+ "SELECT NO_CLIENTE, BE, VENTANILLA, MENSUALIDAD, ID FROM PPC_PCB_PROCESADO";
			try {
				contador = procesos.update(query);
			}catch(Exception ex) {
				throw new GenericException("Error, Paso 0: inserta en tarifas" , HttpStatus.CONFLICT.toString());
			}
	    	return contador;
	    	
	    } 	   

		public int insertaQueryCtosDuplicado() throws GenericException {
	    	int contador = 1;
	    	String query = "INSERT INTO PPC_PCB_QUERY_CTOS_DUPLICADOS(CUENTA, CTA_CUENTA,ID) "
	    			+ "(SELECT CUENTA, COUNT(CUENTA) AS CTA_CUENTA, '1' AS ID "
	    			+ "FROM PPC_PCB_QUERY_CTOS_AGRUPADO "
	    			+ "GROUP BY CUENTA, '1' "
	    			+ "HAVING Count(CUENTA)>1)";
	    	try {
	    		contador = procesos.update(query);
	    	}catch(Exception ex) {
				throw new GenericException("Error, Paso 1: inserta en query en costos duplicados" , HttpStatus.CONFLICT.toString());
			}
	    	return contador;
	    }
	    
	    public int actualizaQueryCtosDuplicados() throws GenericException {
	    	int contador = 2;
	    	String query = "UPDATE PPC_PCB_QUERY_CTOS_AGRUPADO SET DUPLICADO = 'SI' "
	    			+ "WHERE PPC_PCB_QUERY_CTOS_AGRUPADO.CUENTA IN (SELECT DISTINCT(CUENTA) FROM PPC_PCB_QUERY_CTOS_DUPLICADOS)";
	    	try {
	    		contador = procesos.update(query);
	    	}catch(Exception ex) {
				throw new GenericException("Error, Paso 2: actualiza query en costos duplicados" , HttpStatus.CONFLICT.toString());
			}
	    	return contador;
	    }
	    
	    public int insertaCtosUnicos() throws GenericException {
	    	int contador = 3;
	    	String query = "INSERT INTO PPC_PCB_CTOS_UNICOS( NUM_CUENTA, SUC, CTA,USO, MON, FRANQUICIA, ID) "
	    			+ "SELECT CUENTA, PREFMDA, CUENTAMDA, USO, MON, FRANQUICIA,ID "
	    			+ "FROM PPC_PCB_QUERY_CTOS_AGRUPADO";
	    	try {
	    		contador = procesos.update(query);
	    	}catch(Exception ex) {
	    		throw new GenericException("Error, Paso 3: inserta costos únicos" , HttpStatus.CONFLICT.toString());
	    	}
	    	return contador;
	    }
	    
	    public int actualizaCtasVirtalesMesyAnio() throws GenericException {
	    	int contador = 4;
	    	String query = "UPDATE  PPC_PCB_CTAS_VIRTUALES SET MES = extract(month from FEC_ALTA), "
	    			+ "ANIO = extract(year from FEC_ALTA)";
	    	try {
	    		contador = procesos.update(query);
	    	}catch(Exception ex) {
	    		throw new GenericException("Error, Paso 4: actualiza en cuentas virtuales mes y anio" , HttpStatus.CONFLICT.toString());
	    	}
	    	return contador;
	    }
	   
	    public int insertaCtasVirtualesGpos() throws GenericException {
	    	int contador = 5;
	    	String query = "INSERT INTO PPC_PCB_CTAS_VIRTUALES_GPOS(NUM_CLIENTE, NUM_CUENTA, NOMBRE, CTAS_V, TXNS_BE, TXNS_VENT, "
	    			+ "TARIFA_BE,TARIFA_VENT,TARIFA_MENS, COM_BE,COM_VENT,COM_MENS, USO_11,COM_TOTAL, SUC, CUENTA, FRANQUICIA, MONEDA, IVA, ID) "
	    			+ "SELECT NUM_CLIENTE, NUM_CUENTA, NOMBRE, SUM(CUENTAS_X) AS CTAS_V, 0 AS TXNS_BE, 0 AS TXNS_VENT, "
	    			+ "0 AS TARIFA_BE,  0 AS TARIFA_VENT, 0 AS TARIFA_MENS, 0 AS COM_BE, 0 AS COM_VENT, 0 AS COM_MENS, NULL AS USO_11, "
	    			+ "0 AS COM_TOTAL, 0 AS SUC, 0 AS CTA, 0 AS FRANQUICIA, 0 AS MONEDA, 0 AS IVA, ID "
	    			+ "FROM PPC_PCB_CTAS_VIRTUALES "
	    			+ "GROUP BY NUM_CLIENTE, NUM_CUENTA, NOMBRE, 0, ID, NULL";
	    	try {
	    		contador = procesos.update(query);
	    	}catch(Exception ex) {
	    		throw new GenericException("Error, Paso 5: inserta en cuentas virtuales grupos" , HttpStatus.CONFLICT.toString());
	    	}
	    	return contador;
	    }
	    
	    public int actualizaTxsCtasVirtNunSucOperadora() throws GenericException {
	    	int contador = 6;
	    	String query = "UPDATE PPC_PCB_TXS_CTAS_VIRT "
	    			+ "SET TIPO = "
	    			+ "case NUM_SUC_OPERADORA "
	    			+ "when 870 then 'BE' "
	    			+ "when 519 then 'BE' "
	    			+ "when 859 then 'BE' "
	    			+ "else 'VENT' "
	    			+ "end";
	    	try {
	    		contador = procesos.update(query);
	    	}catch(Exception ex) {
	    		throw new GenericException("Error, Paso 6: actualiza txs_ctas_virt" , HttpStatus.CONFLICT.toString());
	    	}
	    	return contador;
	    }
	    
	    public int insertaTxnsXTipo() throws GenericException {
	    	int contador = 7;
	    	String query = "INSERT INTO PPC_PCB_TXNS_X_TIPO(NUM_CLIENTE, NUM_CUENTA,TIPO, CTA_NUM_AUT_TRANS, ID) "
	    			+ "SELECT NUM_CLIENTE, NUM_CTA,TIPO, COUNT(NUM_AUT_TRANS) AS CTA_NUM_AUT_TRANS, ID "
	    			+ "FROM PPC_PCB_TXS_CTAS_VIRT GROUP BY NUM_CLIENTE, NUM_CTA, TIPO, ID";
	    	try {
	    		contador = procesos.update(query);
	    	}catch(Exception ex) {
	    		throw new GenericException("Error, Paso 7: inserta en into txns_x_tipo" , HttpStatus.CONFLICT.toString());
	    	}
	    	return contador;
	    }
	    
	    public int actualizaCtasVirtualesGposTxnsBe() throws GenericException {
	    	int contador = 8;
	    	String query = "MERGE INTO PPC_PCB_CTAS_VIRTUALES_GPOS E "
	    			+ "USING (SELECT R.NUM_CUENTA, R.CTA_NUM_AUT_TRANS, R.TIPO, R.NUM_CLIENTE "
	    			+ "FROM PPC_PCB_TXNS_X_TIPO R) R "
	    			+ "ON (E.NUM_CUENTA=R.NUM_CUENTA AND E.NUM_CLIENTE=R.NUM_CLIENTE) "
	    			+ "WHEN MATCHED THEN "
	    			+ "UPDATE SET E.TXNS_BE=R.CTA_NUM_AUT_TRANS WHERE R.TIPO = 'BE'";
	    	try {
	    		contador = procesos.update(query);
	    	}catch(Exception ex) {
	    		throw new GenericException("Error, Paso 8: Actualiza Ctas_Virtuales_Gpos TXNS_BE" , HttpStatus.CONFLICT.toString());
	    	}
	    	return contador;
	    }
	    
	    public int actualizaCtasVirtualesGposTxnsVent() throws GenericException {
	    	int contador = 9;
	    	String query = "MERGE INTO PPC_PCB_CTAS_VIRTUALES_GPOS E "
	    			+ "USING (SELECT R.NUM_CLIENTE, R.TIPO, R.CTA_NUM_AUT_TRANS, R.NUM_CUENTA "
	    			+ "FROM PPC_PCB_TXNS_X_TIPO R) R "
	    			+ "ON (E.NUM_CLIENTE=R.NUM_CLIENTE AND E.NUM_CUENTA=R.NUM_CUENTA) "
	    			+ "WHEN MATCHED THEN "
	    			+ "UPDATE SET E.COM_VENT=R.CTA_NUM_AUT_TRANS WHERE R.TIPO = 'VENT'";
	    	try {
	    		contador = procesos.update(query);
	    	}catch(Exception ex) {
	    		throw new GenericException("Error, Paso 9: Actualiza Ctas_Virtuales_Gpos TXNS_VENT" , HttpStatus.CONFLICT.toString());
	    	}
	    	return contador;
	    }
	    
	    public int actualizaANull() throws GenericException {
	    	int contador = 10;
	    	String query = "UPDATE PPC_PCB_CTAS_VIRTUALES_GPOS "
	    			+ "SET "
	    			+ "TARIFA_BE = Null, "
	    			+ "TARIFA_VENT = Null, "
	    			+ "TARIFA_MENS = Null";
	    	try {
	    		contador = procesos.update(query);
	    	}catch(Exception ex) {
	    		throw new GenericException("Error, Paso 10: Actualiza Ctas_Virtuales_Gpos TARIFAS a nulo" , HttpStatus.CONFLICT.toString());
	    	}
	    	return contador;
	    }
	    
	    public int actualizaCtasVirtualesGposconTablaTarifas() throws GenericException {
	    	int contador = 11;
	    	String query = "MERGE INTO TSC_EUCS_OWN.PPC_PCB_CTAS_VIRTUALES_GPOS E "
	    			+ "USING (SELECT R.NUM_CLIENTE, R.TARIFA_TX_BE, R.TARIFA_TX_SUC,R.TARIFA_MENSUAL "
	    			+ "FROM TSC_EUCS_OWN.PPC_PCB_TARIFAS R) R "
	    			+ "ON (E.NUM_CLIENTE=R.NUM_CLIENTE) "
	    			+ "WHEN MATCHED THEN "
	    			+ "UPDATE SET E.TARIFA_BE=R.TARIFA_TX_BE, "
	    			+ "E.TARIFA_VENT=R.TARIFA_TX_SUC, "
	    			+ "E.TARIFA_MENS=R.TARIFA_MENSUAL";
	    	try {
	    		contador = procesos.update(query);
	    	}catch(Exception ex) {
	    		throw new GenericException("Error, Paso 11: Actualiza Ctas_Virtuales_Gpos con Tabla Tarifas" , HttpStatus.CONFLICT.toString());
	    	}
	    	return contador;
	    }
	    
	    public int actualizaAUnaTarifaPredefinida() throws GenericException {
	    	int contador = 12;
	    	String query = "UPDATE PPC_PCB_CTAS_VIRTUALES_GPOS "
	    			+ "SET TARIFA_BE = 4, TARIFA_VENT = 14, TARIFA_MENS = 10 "
	    			+ "WHERE (((TARIFA_BE) is null) "
	    			+ "AND ((TARIFA_VENT) is null) "
	    			+ "AND ((TARIFA_MENS) is null))";
	    	try {
	    		contador = procesos.update(query);
	    	}catch(Exception ex) {
	    		throw new GenericException("Error, Paso 12: Actualiza Ctas_Virtuales_Gpos TARIFAS a una tarifa predefinida" , HttpStatus.CONFLICT.toString());
	    	}
	    	return contador;
	    }
	    
	    public int actualizaCtasVirtualesGposBeVentMens() throws GenericException{
	    	int contador = 13;
	    	String query = "UPDATE PPC_PCB_CTAS_VIRTUALES_GPOS "
	    			+ "SET "
	    			+ "COM_BE = TXNS_BE * TARIFA_BE, "
	    			+ "COM_VENT = TXNS_VENT * TARIFA_VENT, "
	    			+ "COM_MENS = CTAS_V * TARIFA_MENS";
	    	try {
	    		contador = procesos.update(query);
	    	}catch(Exception ex) {
	    		throw new GenericException("Error, Paso 13: Actualiza Ctas_Virtuales_Gpos COM_BE,COM_VENT,COM_MENS" , HttpStatus.CONFLICT.toString());
	    	}
	    	return contador;
	    }
	    
	    public int actualizaCtosUnicos() throws GenericException {
	    	int contador = 14;
	    	String query = "MERGE INTO PPC_PCB_CTOS_UNICOS E "
	    			+ "USING (SELECT R.NUM_CUENTA, R.SUC, R.CUENTA, R.FRANQUICIA, R.MONEDA "
	    			+ "FROM PPC_PCB_CTAS_VIRTUALES_GPOS R) R "
	    			+ "ON (E.NUM_CUENTA=R.NUM_CUENTA) "
	    			+ "WHEN MATCHED THEN "
	    			+ "UPDATE SET E.SUC=R.SUC, "
	    			+ "E.CTA=R.CUENTA, "
	    			+ "E.MON=R.MONEDA, "
	    			+ "E.FRANQUICIA=R.FRANQUICIA";
	    	try {
	    		contador = procesos.update(query);
	    	}catch(Exception ex) {
	    		throw new GenericException("Error, Paso 14: Actualiza Ctos_Unicos" , HttpStatus.CONFLICT.toString());
	    	}
	    	return contador;
	    }
	    
	    public int actualizaCtosUnicosUso11() throws GenericException {
	    	int contador = 0;
	    	String query = "MERGE INTO PPC_PCB_CTAS_VIRTUALES_GPOS E "
	    			+ "USING (SELECT R.NUM_CUENTA,R.USO "
	    			+ "FROM PPC_PCB_CTOS_UNICOS R) R "
	    			+ "ON (E.NUM_CUENTA=R.NUM_CUENTA) "
	    			+ "WHEN MATCHED THEN "
	    			+ "UPDATE SET E.USO_11 = 'USO11' WHERE R.USO = '11'";
	    	try {
	    		procesos.update(query);
	    	}catch(Exception ex) {
	    		throw new GenericException("Error, Paso 15: Actualiza Ctos_Unicos USO11" , HttpStatus.CONFLICT.toString());
	    	}
	    	return contador;
	    }
	    
	    public int actualizaCtasVirtualesGposComTotal() throws GenericException {
	    	int contador = 16;
	    	String query = "UPDATE PPC_PCB_CTAS_VIRTUALES_GPOS SET PPC_PCB_CTAS_VIRTUALES_GPOS.COM_TOTAL = "
	    			+ "case PPC_PCB_CTAS_VIRTUALES_GPOS.USO_11 "
	    			+ "when 'USO_11' then PPC_PCB_CTAS_VIRTUALES_GPOS.COM_MENS "
	    			+ "else PPC_PCB_CTAS_VIRTUALES_GPOS.COM_BE + PPC_PCB_CTAS_VIRTUALES_GPOS.COM_VENT + PPC_PCB_CTAS_VIRTUALES_GPOS.COM_MENS "
	    			+ "end";
	    	try {
	    	contador = procesos.update(query);
	    	}catch(Exception ex) {
	    		throw new GenericException("Error, Paso 16: Actualiza Ctas_Virtuales_Gpos COM_TOTAL" , HttpStatus.CONFLICT.toString());
	    	}
	    	return contador;
	    }
	    
	    public int actualizaCtasVirtualesGposIva() throws GenericException {
	    	int contador = 17;
	    	String query = "UPDATE PPC_PCB_CTAS_VIRTUALES_GPOS SET IVA = 16 "
	    			+ "WHERE (((IVA)=0))";
	    	try {
	    		contador = procesos.update(query);
	    	}catch(Exception ex) {
	    		throw new GenericException("Error, Paso 17: Actualiza Ctas_Virtuales_Gpos Iva" , HttpStatus.CONFLICT.toString());
	    	}
	    	return contador;
	    }
	    
	    public int insertaEnLayoutBe() throws GenericException {
	    	int contador = 18;
	    	String query = "INSERT INTO PPC_PCB_LAYOUT_BE(NUM_CLIENTE, NOMBRE, SUC, CTA, COM_BE, "
	    			+ "MONTO_IVA, MONTO_TOTAL, ANIO,MES,PRODUCTO,IVA, MONEDA, USO_11, ID) "
	    			+ "SELECT NUM_CLIENTE, NOMBRE, SUC, CUENTA, COM_BE, "
	    			+ "COM_BE *(IVA/100) AS MONTO_IVA, "
	    			+ "COM_BE *(IVA/100) + COM_BE AS MONTO_TOTAL, "
	    			+ "extract(year from systimestamp) AS ANIO, "
	    			+ "extract(month from (systimestamp)) AS MES, "
	    			+ "'Cobranza Universal' AS PRODUCTO, IVA, MONEDA, USO_11,ID "
	    			+ "FROM PPC_PCB_CTAS_VIRTUALES_GPOS";
	    	try {
	    		contador = procesos.update(query);
	    	}catch(Exception ex) {
	    		throw new GenericException("Error, Paso 18: inserta en Layout_BE" , HttpStatus.CONFLICT.toString());
	    	}
	    	return contador;
	    }
	    
	    public int insertaEnLayoutVent() throws GenericException {
	    	int contador = 19;
	    	String query = "INSERT INTO PPC_PCB_LAYOUT_VENT(NUM_CLIENTE,NOMBRE,SUC,CTA,COM_VENT, "
	    			+ "MONTO_IVA,MONTO_TOTAL,ANIO,MES,PRODUCTO,IVA,MONEDA,USO_11,ID) "
	    			+ "SELECT NUM_CLIENTE, NOMBRE,SUC, NUM_CUENTA,COM_VENT, "
	    			+ "COM_VENT*(IVA/100) AS MONTO_IVA, "
	    			+ "COM_VENT*(IVA/100) + COM_VENT AS MONTO_TOTAL, "
	    			+ "extract(year from (systimestamp)) AS ANIO, "
	    			+ "extract(month from (systimestamp)) AS MES, "
	    			+ "'Cobranza Universal' AS PRODUCTO, IVA, MONEDA, USO_11,ID "
	    			+ "FROM PPC_PCB_CTAS_VIRTUALES_GPOS";
	    	try {
	    		contador = procesos.update(query);
	    	}catch(Exception ex) {
	    		throw new GenericException("Error, Paso 19: inserta en Layout_VENT" , HttpStatus.CONFLICT.toString());
	    	}
	    	return contador;
	    }
	    
	    public int insertaEnLayoutMens() throws GenericException {
	    	int contador = 20;
	    	String query = "INSERT INTO PPC_PCB_LAYOUT_MENS(NUM_CLIENTE,NOMBRE,SUC,CTA, "
	    			+ "COM_MENS,MONTO_IVA, MONTO_TOTAL,ANIO,MES,PRODUCTO,IVA,MONEDA,ID) "
	    			+ "SELECT NUM_CLIENTE, NOMBRE, "
	    			+ "SUC, CUENTA, COM_MENS, "
	    			+ "COM_MENS*(IVA/100) AS MONTO_IVA, "
	    			+ "COM_MENS*(IVA/100) + COM_MENS AS MONTO_TOTAL, "
	    			+ "extract(year from systimestamp) AS ANIO, "
	    			+ "extract(month from systimestamp) AS MES, "
	    			+ "'Cobranza Universal' AS PRODUCTO, IVA, MONEDA, ID "
	    			+ "FROM PPC_PCB_CTAS_VIRTUALES_GPOS";
	    	try {
	    		contador = procesos.update(query);
	    	}catch(Exception ex) {
	    		throw new GenericException("Error, Paso 20: inserta en Layout_MENS" , HttpStatus.CONFLICT.toString());
	    	}
	    	return contador;
	    }
	    
	    public int insertaEnLayoutTxnsConImporte() throws GenericException {
	    	int contador = 21;
	    	String query = "INSERT INTO PPC_PCB_TXNS_IMPORTE(NUM_CLIENTE,NUM_CUENTA,TIPO,TXNS,SUM_IMP_TRANS,ID) "
	    			+ "SELECT NUM_CLIENTE,NUM_CTA,TIPO, Count(NUM_CLIENTE) AS TXNS, Sum(IMP_TRANSACCION) AS SUM_IMP_TRANS,ID "
	    			+ "FROM PPC_PCB_TXS_CTAS_VIRT "
	    			+ "GROUP BY NUM_CLIENTE, NUM_CTA, TIPO, ID";
	    	try {
	    		contador = procesos.update(query);
	    	}catch(Exception ex) {
	    		throw new GenericException("Error, Paso 21: inserta en Layout_Txns_Con_Importe" , HttpStatus.CONFLICT.toString());
	    	}
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

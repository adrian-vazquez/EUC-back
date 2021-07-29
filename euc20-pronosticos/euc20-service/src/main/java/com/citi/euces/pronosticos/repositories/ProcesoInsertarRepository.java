package com.citi.euces.pronosticos.repositories;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class ProcesoInsertarRepository {
	private final JdbcTemplate procesos;

    public ProcesoInsertarRepository(JdbcTemplate procesos) {
    this.procesos = procesos;
    }
    
    public int cargaLayout() {
    	String query = "";
    	int cifra = procesos.update(query);
    	return cifra;
    }
    
    public int updateLayout() {
    	String query = "";
    	int cifra = procesos.update(query);
    	return cifra;
    }
    
    public int insertaArmadoCuerpo() {
    	String query = "";
    	int cifra = procesos.update(query);
    	return cifra;
    }
    
    public int llamaProcedimiento() {
    	String query = "";
    	int cifra = procesos.update(query);
    	return cifra;
    }
    
    public int updateArmadoCuerpo(int dias, int secuencial) {
    	String query = "UPDATE PPC_PCB_ARMADO_CUERPO "
    			+ "SET PERIODO_PROTECCION = " + dias
    			+ " SEC_ARC_ORIGINAL = " + secuencial;
    	int cifra = procesos.update(query);
    	return cifra;
    }
    
    public int insertaPreparoCuerpo() {
    	String query = "INSERT INTO PPC_PCB_PREPARO_CUERPO ( "
    			+ "TIPO_REGISTRO, NUM_SECUENCIA,  COD_OPERACION, "
    			+ "COD_DIVISA,  IMPORTE_OPERACION,  FEC_APLICACION, "
    			+ "PERIODO_PROTECCION,  NUM_CLIENTE, USO_FUTURO_1, "
    			+ "TIPO_OPERACION, FEC_VENCIMIENTO,  BANCO_RECEPTOR, "
    			+ "TIPO_CTA_CLIENTE,  CTA_CLIENTE,  NOM_CLIENTE, "
    			+ "REF_EMISOR, USO_FUTURO_2, TITULAR_SERVICIO, "
    			+ "SALDO_PROTECCION,  REF_NUM_EMISOR, REF_LEYENDA_EMISOR, "
    			+ "MOTIVO_RECHAZO, USO_FUTURO_3,  NUM_PROTECCION, "
    			+ "USO_FUTURO_4, INICIO_PROTECCION,  SEC_ARC_ORIGINAL, "
    			+ "REFERENCIA_1, REFERENCIA_2,  REFERENCIA_3_USO_FUTURO, "
    			+ "DESC_RECHAZO, CNTR_CLIENTE_USR, CONCEPTO, "
    			+ "SUBCONCEPTO, NUM_MES_COBRO, SEC_REG_ORIGINAL, "
    			+ "CTA_ABONO, TIPO_CTA_ABONO, CONCEPTO_COBRO_2, "
    			+ "SUBCONCEPTO_COBRO_2, IMPORTE_2,  USO_FUTURO_5, ID ) "
    			+ "SELECT PPC_PCB_ARMADO_CUERPO.TIPO_REGISTRO, PPC_PCB_ARMADO_CUERPO.NUM_SECUENCIA, "
    			+ "PPC_PCB_ARMADO_CUERPO.COD_OPERACION, PPC_PCB_ARMADO_CUERPO.COD_DIVISA, "
    			+ "PPC_PCB_ARMADO_CUERPO.IMPORTE_OPERACION, PPC_PCB_ARMADO_CUERPO.FEC_APLICACION, "
    			+ "PPC_PCB_ARMADO_CUERPO.PERIODO_PROTECCION, PPC_PCB_ARMADO_CUERPO.NUM_CLIENTE, "
    			+ "PPC_PCB_ARMADO_CUERPO.USO_FUTURO_1,  PPC_PCB_ARMADO_CUERPO.TIPO_OPERACION, "
    			+ "PPC_PCB_ARMADO_CUERPO.FEC_VENCIMIENTO, PPC_PCB_ARMADO_CUERPO.BANCO_RECEPTOR, "
    			+ "PPC_PCB_ARMADO_CUERPO.TIPO_CTA_CLIENTE, PPC_PCB_ARMADO_CUERPO.CTA_CLIENTE, "
    			+ "PPC_PCB_ARMADO_CUERPO.NOM_CLIENTE, PPC_PCB_ARMADO_CUERPO.REF_EMISOR, "
    			+ "PPC_PCB_ARMADO_CUERPO.USO_FUTURO_2, PPC_PCB_ARMADO_CUERPO.TITULAR_SERVICIO, "
    			+ "PPC_PCB_ARMADO_CUERPO.SALDO_PROTECCION, PPC_PCB_ARMADO_CUERPO.REF_NUM_RECEPTOR, "
    			+ "PPC_PCB_ARMADO_CUERPO.REF_LEYENDA_EMISOR, PPC_PCB_ARMADO_CUERPO.MOTIVO_RECHAZO, "
    			+ "PPC_PCB_ARMADO_CUERPO.USO_FUTURO_3, PPC_PCB_ARMADO_CUERPO.NUM_PROTECCION, "
    			+ "PPC_PCB_ARMADO_CUERPO.USO_FUTURO_4, PPC_PCB_ARMADO_CUERPO.INICIO_PROTECCION, "
    			+ "PPC_PCB_ARMADO_CUERPO.SEC_ARC_ORIGINAL, PPC_PCB_ARMADO_CUERPO.REFERENCIA_1, "
    			+ "PPC_PCB_ARMADO_CUERPO.REFERENCIA_2,  PPC_PCB_ARMADO_CUERPO.REFERENCIA_3_USO_FUTURO, "
    			+ "PPC_PCB_ARMADO_CUERPO.DESC_RECHAZO, PPC_PCB_ARMADO_CUERPO.CNTR_CLIENTE_USR, "
    			+ "PPC_PCB_ARMADO_CUERPO.CONCEPTO, PPC_PCB_ARMADO_CUERPO.SUBCONCEPTO, "
    			+ "PPC_PCB_ARMADO_CUERPO.NUM_MES_COBRO, PPC_PCB_ARMADO_CUERPO.SEC_REG_ORI, "
    			+ "PPC_PCB_ARMADO_CUERPO.CTA_ABONO, PPC_PCB_ARMADO_CUERPO.TIPO_CTA_ABONO, "
    			+ "PPC_PCB_ARMADO_CUERPO.CONCEPTO_COBRO_2, PPC_PCB_ARMADO_CUERPO.SUBCONCEPTO_COBRO_2, "
    			+ "PPC_PCB_ARMADO_CUERPO.IMPORTE_2, PPC_PCB_ARMADO_CUERPO.USO_FUTURO_5, "
    			+ "PPC_PCB_ARMADO_CUERPO.ID "
    			+ "FROM PPC_PCB_ARMADO_CUERPO";
    	int cifra = procesos.update(query);
    	return cifra;
    }
    
    public int updatePreparoCuerpo() {
    	String query = "";
    	int cifra = procesos.update(query);
    	return cifra;
    }
    
    public int armaHeader(int secuencial) {
    	String query = "";
    	int cifra = procesos.update(query);
    	return cifra;
    }
    
    public int armaTrailer() {
    	String query = "";
    	int cifra = procesos.update(query);
    	return cifra;
    }
    
    public int confirmaPerfiles(int secuencial) {
    	String query = "";
    	int cifra = procesos.update(query);
    	return cifra;
    }
}

package com.citi.euces.pronosticos.repositories;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Date;
import java.util.List;
import javax.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ParameterizedPreparedStatementSetter;
import org.springframework.stereotype.Repository;

import com.citi.euces.pronosticos.infra.dto.SubirRespuestaDTO;

@Repository
public class SubirArchivosCobroJDBCRepository {
	static final Logger log = LoggerFactory.getLogger(SubirArchivosCobroJDBCRepository.class);
	
	private final JdbcTemplate jdbcTemplate;

    public SubirArchivosCobroJDBCRepository(JdbcTemplate jdbcTemplate) {
    	this.jdbcTemplate = jdbcTemplate;
	}

    public void truncateTmpRespuesta() {
        String query = "DELETE FROM PPC_PCB_RESPUESTA";
        jdbcTemplate.execute(query);
    }
    
    @Transactional
    public int[][] batchInsert(List<SubirRespuestaDTO> content, int batchSize) {
        int[][] updateCounts = jdbcTemplate.batchUpdate(
                "INSERT INTO PPC_PCB_RESPUESTA "
                + "values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,null)",
                content,
                batchSize,
                new ParameterizedPreparedStatementSetter<SubirRespuestaDTO>() {
                    public void setValues(PreparedStatement ps, SubirRespuestaDTO argument) throws SQLException {

                        ps.setString(1, argument.getTipoRegistro());
                        ps.setLong(2, argument.getNumSecuencia());
                        ps.setString(3, argument.getCodOperacion());
                        ps.setString(4, argument.getCodDivisa());
                        ps.setDouble(5, argument.getImporteOperacion());
                        ps.setDate(6, new Date (argument.getFecAplicacion().getTime()));
                        ps.setString(7, argument.getPeriodoProteccion());
                        ps.setString(8, argument.getNumCliente());
                        ps.setString(9, argument.getTipoOperacion());
                        ps.setDate(10, new Date (argument.getFecVencimiento().getTime()));
                        ps.setString(11, argument.getBancoReceptor());
                        ps.setString(12, argument.getTipoCtaCliente());
                        ps.setString(13, argument.getCtaCliente());
                        ps.setString(14, argument.getNomCliente());
                        ps.setString(15, argument.getRefEmisor());
                        ps.setString(16, argument.getUsoFuturo1());
                        ps.setString(17, argument.getUsoFuturo2());
                        ps.setString(18, argument.getUsoFuturo3());
                        ps.setString(19, argument.getUsoFuturo4());
                        ps.setString(20, argument.getUsoFuturo5());
                        ps.setString(21, argument.getTitularServicio());
                        ps.setDouble(22, argument.getSaldoProteccion());
                        ps.setString(23, argument.getRefEmisor());
                        ps.setString(24, argument.getRefLeyendaEmisor());
                        ps.setString(25, argument.getMotivoRechazo());
                        ps.setString(26, argument.getNumProteccion());
                        ps.setString(27, argument.getInicioProteccion());
                        ps.setString(28, argument.getSecArcOriginal());
                        ps.setString(29, argument.getReferencia1());
                        ps.setString(30, argument.getReferencia2());
                        ps.setString(31, argument.getReferencia3UsuFuturo());
                        ps.setString(32, argument.getDescRechazo());
                        ps.setString(33, argument.getCntrClienteUsr());
                        ps.setString(34, argument.getConcepto());
                        ps.setString(35, argument.getSubConcepto());
                        ps.setLong(36, argument.getNumMesCobro());
                        ps.setLong(37, argument.getSecRegOriginal());
                        ps.setString(38, argument.getCtaAbono());
                        ps.setString(39, argument.getTipoCtaAbono());
                        ps.setString(40, argument.getConceptoCobro2());
                        ps.setString(41, argument.getSubconceptoCobro2());
                        ps.setDouble(42, argument.getImporte2());    
                       
                    }
                });
        return updateCounts;
    }
    
    public void InsertPreparoResp() {
    	String Query = "INSERT INTO PPC_PCB_PREPARO_RESPUESTA(TIPO_REGISTRO,NUM_SECUENCIA,COD_OPERACION,COD_DIVISA,IMPORTE_OPERACION,FEC_APLICACION, "
    			+ "PERIODO_PROTECCION, NUM_CLIENTE, TIPO_OPERACION, FEC_VENCIMIENTO, BANCO_RECEPTOR, TIPO_CTA_CLIENTE, CTA_CLIENTE,"
    			+ "NOM_CLIENTE, REF_EMISOR, USO_FUTURO_1, USO_FUTURO_2, USO_FUTURO_3, USO_FUTURO_4, USO_FUTURO_5, TITULAR_SERVICIO,"
    			+ "SALDO_PROTECCION, REF_NUM_EMISOR, REF_LEYENDA_EMISOR, MOTIVO_RECHAZO, NUM_PROTECCION, INICIO_PROTECCION,"
    			+ "SEC_ARC_ORIGINAL, REFERENCIA_1, REFERENCIA_2, REFERENCIA_3_USO_FUTURO, DESC_RECHAZO, CNTR_CLIENTE_USR, CONCEPTO,"
    			+ "SUBCONCEPTO, NUM_MES_COBRO, SEC_REG_ORIGINAL, CTA_ABONO, TIPO_CTA_ABONO, CONCEPTO_COBRO_2, IMPORTE_2, CAMBIO_NUMERO, LLAVE) "
    			+ "SELECT TIPO_REGISTRO,NUM_SECUENCIA,COD_OPERACION,COD_DIVISA,IMPORTE_OPERACION,FEC_APLICACION,"
    			+ "PERIODO_PROTECCION, NUM_CLIENTE, TIPO_OPERACION, FEC_VENCIMIENTO, BANCO_RECEPTOR, TIPO_CTA_CLIENTE, CTA_CLIENTE,"
    			+ "NOM_CLIENTE, REF_EMISOR, USO_FUTURO_1, USO_FUTURO_2, USO_FUTURO_3, USO_FUTURO_4, USO_FUTURO_5, TITULAR_SERVICIO,"
    			+ "SALDO_PROTECCION, REF_NUM_EMISOR, REF_LEYENDA_EMISOR, MOTIVO_RECHAZO, NUM_PROTECCION, INICIO_PROTECCION,"
    			+ "SEC_ARC_ORIGINAL, REFERENCIA_1, REFERENCIA_2, REFERENCIA_3_USO_FUTURO, DESC_RECHAZO, CNTR_CLIENTE_USR, CONCEPTO,"
    			+ "SUBCONCEPTO, NUM_MES_COBRO, SEC_REG_ORIGINAL, CTA_ABONO, TIPO_CTA_ABONO, CONCEPTO_COBRO_2, IMPORTE_2,NULL,NULL "
    			+ "FROM PPC_PCB_RESPUESTA";
    	jdbcTemplate.execute(Query);
    }
    
    
    public void UpdatePreparoResp(){
    	String Query = "UPDATE PPC_PCB_PREPARO_RESPUESTA "
    			+ "SET tipo_registro = null "
    			+ "WHERE (((tipo_registro)='01' OR (tipo_registro)='09'))";
    	jdbcTemplate.execute(Query);
    }
    
    public void DeleteTipoRegistro() {
    	String Query="DELETE FROM PPC_PCB_PREPARO_RESPUESTA WHERE tipo_registro is null";
    	jdbcTemplate.execute(Query);
    }
    
    public void UpdateNumSecuencia() {
    	String Query = "UPDATE PPC_PCB_PREPARO_RESPUESTA SET num_secuencia = num_secuencia";
    	jdbcTemplate.execute(Query);
    }
    
    public void UpdateCambioNumero() {
    	String Query = "UPDATE PPC_PCB_PREPARO_RESPUESTA SET cambio_numero = sec_arc_original WHERE tipo_registro is not null;";
    	jdbcTemplate.execute(Query);
    }
}

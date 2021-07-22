package com.citi.euces.pronosticos.repositories;

import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.citi.euces.pronosticos.infra.dto.LayoutProteccionDTO;
@Repository
public class LayoutProteccionJDBCRepository {
	
	private JdbcTemplate jdbc;
	
	public LayoutProteccionJDBCRepository(JdbcTemplate jdbc){
		this.jdbc=jdbc;
		
	}
	
	public List<LayoutProteccionDTO> getDataLayout(){
		String sql="SELECT c1,c2,c3,c4,c5,c6,c7,c8,c9,c10,c11,c12,c13,c14,c15,c16 from (" + 
				"SELECT concat(Tipo_Registro, CONCAT(NUM_Secuencia, CONCAT(cod_operacion, CONCAT(banco_participante, CONCAT(sentido, CONCAT(servicio, " + 
				"CONCAT(num_bloqueo, CONCAT(fec_presentacion, CONCAT(cod_divisas,causa_rechazo)))))))))as c1, '                         ' as c2, " + 
				"razon_social_emisor as c3, CONCAT('          ',CONCAT(RFC_EMISOR, '     ')) as c4,' ' as c5, " + 
				"'                                                            ' as c6,'                    ' as c7, " + 
				"'                                                            ' as c8, '                                  ' as c9, '        ' as c10, " + 
				" concat(NUM_CLIENTE,sec_archivo) as c11,'  ' as c12, '            ' as c13,'' as c14, '' as c15, " + 
				" '                                                                                                                                                                                                                    ' as c16, 0 as id " + 
				" from ppc_pcb_header " + 
				"union all " + 
				"SELECT CONCAT(tipo_registro, CONCAT(num_secuencia, CONCAT(cod_operacion, CONCAT(cod_divisa, CONCAT(importe_operacion, CONCAT(fec_aplicacion, CONCAT(periodo_proteccion,num_cliente))))))) as c1, " + 
				" '         ' as c2, CONCAT(tipo_operacion, CONCAT(fec_vencimiento, CONCAT(banco_receptor, CONCAT(tipo_cta_cliente, CASE WHEN cta_cliente IS NULL THEN '00000000000000000000' else cta_cliente end))))as c3, " + 
				"'                                        ' as c4, ref_emisor as c5, '                                                            ' as c6, " + 
				"CONCAT(saldo_proteccion, CONCAT(ref_num_emisor,ref_leyenda_emisor)) as c7, RPAD('',40-length(ref_leyenda_emisor),' ') as c8, motivo_rechazo as c9,  '       ' as c10, " + 
				"num_proteccion as c11, '           ' as c12, CONCAT(inicio_proteccion,sec_arc_original) as c13, " + 
				"'                                                                                                    ' as c14, " + 
				"CONCAT(cntr_cliente_usr, CONCAT(concepto, CONCAT(subconcepto, CONCAT(num_mes_cobro, CONCAT(sec_reg_original, CONCAT(cta_abono, CONCAT(tipo_cta_abono, CONCAT(concepto_cobro_2, CONCAT(subconcepto_cobro_2,importe_2))))))))) as c15, " + 
				"'                                                 ' as c16, id "  + 
				" FROM PPC_PCB_PREPARO_CUERPO " + 
				"union all " + 
				"SELECT CONCAT(tipo_registro, CONCAT(num_secuencia, CONCAT(cod_operacion, CONCAT(num_bloque, CONCAT(num_operaciones,imp_tot_operaciones))))) as c1, " + 
				"'' as c2,'' as c3, '' as c4, '' as c5, '' as c6, '' as c7, '' as c8, '' as c9, '' as c10,"
				+ "'                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                 ' as c11,"
				+ " '' as c12, '' as c13, " + 
				"'' as c14, '' as c15, '' as c16,  66000000 as id " + 
				"FROM PPC_PCB_TRAILER ) a order by id asc";
		
		return jdbc.query(sql, (cc,rownum)->new LayoutProteccionDTO(cc.getString(1),
				cc.getString(2),cc.getString(3),cc.getString(4),cc.getString(5),cc.getString(6),
				cc.getString(7),cc.getString(8),cc.getString(9),cc.getString(10),cc.getString(11),cc.getString(12),
				cc.getString(13),cc.getString(14),cc.getString(15),cc.getString(16)));
	}
				
}

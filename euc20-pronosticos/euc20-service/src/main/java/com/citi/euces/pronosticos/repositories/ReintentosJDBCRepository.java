package com.citi.euces.pronosticos.repositories;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.citi.euces.pronosticos.infra.dto.BancaDTO;
import com.citi.euces.pronosticos.infra.dto.GenArcPFECyTCDTO;

@Repository
public class ReintentosJDBCRepository {

	private final JdbcTemplate jdbcTemplate;

	public ReintentosJDBCRepository(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	@Transactional
	public void truncateTableReintentos() {
		String query = "DELETE FROM PPC_MIS_REINTENTOS";
		jdbcTemplate.execute(query);
	}
	
	@Transactional
    public List<GenArcPFECyTCDTO> ConteoReintentosSemanalesPersonaFisica(Integer mes1, Integer anio1, Integer dias1, Integer mes2, Integer anio2, Integer dias2,
			Integer mes3, Integer anio3, Integer dias3) {
        
        String sql = "SELECT COUNT(*) AS conteo " +
                "FROM PPC_MIS_MAESTRO_COMISIONES mc " +
                "WHERE mc.ID_ESTATUS_COMISION=3 AND mc.CATALOGADA_GC=5 AND mc.ID_SERVICIO=10 AND mc.ID_ONDEMAND=32" +
                " AND (";

				 if (dias1 > 0)
					 sql = sql + "mc.MES=" + mes1 + " AND mc.ANIO=" + anio1;
				 if (dias2 > 0)
					 sql = sql + " OR mc.MES=" + mes2 + " AND mc.ANIO=" + anio2;
				 if (dias3 > 0)
					 sql = sql + " OR mc.MES=" + mes3 + " AND mc.ANIO=" + anio3;
				
				 sql = sql.replace("( OR", "(");
				
				 sql = sql + " )";
 
        System.out.println("QUERY_SQL_Clientes :: ejecute:: " + sql);

        return jdbcTemplate.query( sql,(cc, rowNum) ->
                new GenArcPFECyTCDTO(
                        cc.getInt("CONTEO")
                )
        );
    }
	
	@Transactional
    public List<GenArcPFECyTCDTO> ConteoReintentosSemanalesExtraContable(Integer mes1, Integer anio1, Integer dias1, Integer mes2, Integer anio2, Integer dias2,
			Integer mes3, Integer anio3, Integer dias3) {
        
        String sql = "SELECT COUNT(*) AS conteo " +
                "FROM PPC_MIS_MAESTRO_COMISIONES mc " +
                "WHERE mc.ID_ESTATUS_COMISION=3 AND mc.CATALOGADA_GC=1" +
                " AND (";

				 if (dias1 > 0)
					 sql = sql + "mc.MES=" + mes1 + " AND mc.ANIO=" + anio1;
				 if (dias2 > 0)
					 sql = sql + " OR mc.MES=" + mes2 + " AND mc.ANIO=" + anio2;
				 if (dias3 > 0)
					 sql = sql + " OR mc.MES=" + mes3 + " AND mc.ANIO=" + anio3;
				
				 sql = sql.replace("( OR", "(");
				
				 sql = sql + " )";
 
        System.out.println("QUERY_SQL_Clientes :: ejecute:: " + sql);

        return jdbcTemplate.query( sql,(cc, rowNum) ->
                new GenArcPFECyTCDTO(
                        cc.getInt("CONTEO")
                )
        );
    }
	
	@Transactional
    public List<GenArcPFECyTCDTO> ConteoReintentosSemanalesTasaCero() {
        
        String sql = "SELECT COUNT(*) AS conteo " +
                "FROM PPC_MIS_MAESTRO_COMISIONES mc " +
                "WHERE mc.ID_ESTATUS_COMISION=3 AND mc.CATALOGADA_GC=5 AND (mc.ID_SERVICIO=21 and mc.ID_ONDEMAND=31);";
        
        System.out.println("QUERY_SQL_Clientes :: ejecute:: " + sql);

        return jdbcTemplate.query( sql,(cc, rowNum) ->
                new GenArcPFECyTCDTO(
                        cc.getInt("CONTEO")
                )
        );
    }

}

package com.citi.euces.pronosticos.repositories;

import com.citi.euces.pronosticos.infra.dto.CuentasContablesDTO;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public class CuentasContablesJDBCRepository {

    private final JdbcTemplate jdbcTemplate;

    public CuentasContablesJDBCRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Transactional
    public List<CuentasContablesDTO> findAll() {
        String sql = "SELECT * FROM PPC_MIS_CUENTAS_CONTABLES";
        return jdbcTemplate.query(sql, (cc, rowNum) ->
                new CuentasContablesDTO(
                        cc.getLong("CUENTA"),
                        cc.getString("PRODUCTO"),
                        cc.getString("MODELO_GESTION"),
                        cc.getString("DESCRIPCION"),
                        cc.getString("SERVICIO"),
                        cc.getLong("ID_SERVICIO"),
                        cc.getLong("ID_ONDEMAND")
                )
        );

    }

}

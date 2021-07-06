package com.citi.euces.pronosticos.repositories;

import com.citi.euces.pronosticos.infra.dto.CatServiciosPronosticosDTO;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public class CatServiciosPronosticoJDBCRepository  {

    private final JdbcTemplate jdbcTemplate;

    public CatServiciosPronosticoJDBCRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Transactional
    public List<CatServiciosPronosticosDTO> findAll() {
        String sql = "SELECT * FROM PPC_MIS_CATALOGO_SERVICIOS_PRONOSTICO";
        return jdbcTemplate.query(sql, (sp, rowNum) ->
                        new CatServiciosPronosticosDTO(
                                sp.getLong("ID_SERVICIO"),
                                sp.getLong("ID_ONDEMAND"),
                                sp.getString("SERVICIO")
                        )
        );

    }
}

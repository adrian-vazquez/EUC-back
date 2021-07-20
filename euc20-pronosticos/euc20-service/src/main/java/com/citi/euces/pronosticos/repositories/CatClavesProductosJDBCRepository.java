package com.citi.euces.pronosticos.repositories;

import com.citi.euces.pronosticos.infra.dto.CatClavesProductosDTO;
import com.citi.euces.pronosticos.infra.dto.CatServiciosPronosticosDTO;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public class CatClavesProductosJDBCRepository {

    private final JdbcTemplate jdbcTemplate;

    public CatClavesProductosJDBCRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Transactional
    public List<CatClavesProductosDTO> findListProducts() {
        String sql = "SELECT * FROM PPC_MIS_CATALOGO_CLAVES_PRODUCTOS order by ID_CLAVE_PRODUCTO";
        return jdbcTemplate.query(sql, (sp, rowNum) ->
                new CatClavesProductosDTO(
                        sp.getLong("ID_CLAVE_PRODUCTO"),
                        sp.getString("PRODUCTO"),
                        sp.getString("CLAVE")
                )
        );

    }
}

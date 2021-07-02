package com.citi.euces.pronosticos.repositories;

import com.citi.euces.pronosticos.infra.dto.RebNumProtectDTO;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ParameterizedPreparedStatementSetter;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

@Repository
public class RebNumProtectJDBCRepository {

    private final JdbcTemplate jdbcTemplate;

    public RebNumProtectJDBCRepository(JdbcTemplate jdbcTemplate) {
    this.jdbcTemplate = jdbcTemplate;
    }

    @Transactional
    public int[][] batchInsert(List<RebNumProtectDTO> books, int batchSize) {
        System.out.println("repository :: content :: " + books.size());

        int[][] updateCounts = jdbcTemplate.batchUpdate(
                "INSERT INTO PPC_MIS_REB_NUMPROTEC(NUM_PROTECCION, FEC_MOVIMIENTO, FEC_REGISTRO_CONTABLE) values(?,?,?)",
                books,
                batchSize,
                new ParameterizedPreparedStatementSetter<RebNumProtectDTO>() {
                    public void setValues(PreparedStatement ps, RebNumProtectDTO argument) throws SQLException {

                        ps.setLong(1, argument.getNumProteccion());
                        ps.setDate(2, new Date(argument.getFechaMovimiento().getTime()));
                        ps.setDate(3, new Date(argument.getFechaMovimiento().getTime()));
                    }
                });
        return updateCounts;
    }

}

package com.citi.euces.pronosticos.repositories;

import com.citi.euces.pronosticos.infra.dto.RebNumProtectDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ParameterizedPreparedStatementSetter;
import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Types;
import java.util.List;
import java.util.Map;

@Repository
public class RebNumProtectJDBCRepository {

    static final Logger log = LoggerFactory.getLogger(RebNumProtectJDBCRepository.class);
    private final JdbcTemplate jdbcTemplate;
    private SimpleJdbcCall simpleJdbcCallRefCursor;

    public RebNumProtectJDBCRepository(JdbcTemplate jdbcTemplate) {
    this.jdbcTemplate = jdbcTemplate;
    }

    @Transactional
    public int[][] batchInsert(List<RebNumProtectDTO> content, int batchSize) {
        int[][] updateCounts = jdbcTemplate.batchUpdate(
                "INSERT INTO PPC_MIS_REB_NUMPROTEC(NUM_PROTECCION, FEC_MOVIMIENTO, FEC_REGISTRO_CONTABLE) values(?,?,?)",
                content,
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

    @Transactional
    public String updateMaestoComisionesSp(){
        jdbcTemplate.setResultsMapCaseInsensitive(true);
        simpleJdbcCallRefCursor = new SimpleJdbcCall(jdbcTemplate)
                .withProcedureName("PPC_MIS_SP_REBAJA_MAESTRO_COMISIONES")
                .declareParameters(new SqlParameter("p_numRegCargados", Types.INTEGER),
                new SqlOutParameter("p_numRegCargados", Types.INTEGER));
        Map<String, Object> out = simpleJdbcCallRefCursor.execute(new MapSqlParameterSource("p_numRegCargados", 0));
        log.info("updateMaestoComisionesSp p_numRegCargados :: >> " +  out.get("p_numRegCargados"));
        return out.get("p_numRegCargados").toString();
    }

}

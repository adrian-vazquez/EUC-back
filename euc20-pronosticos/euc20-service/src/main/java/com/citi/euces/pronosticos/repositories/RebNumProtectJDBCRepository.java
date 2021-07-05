package com.citi.euces.pronosticos.repositories;

import com.citi.euces.pronosticos.infra.dto.RebNumProtectDTO;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ParameterizedPreparedStatementSetter;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

@Repository
public class RebNumProtectJDBCRepository {

    private final JdbcTemplate jdbcTemplate;

    private SimpleJdbcCall simpleJdbcCallRefCursor;


    public RebNumProtectJDBCRepository(JdbcTemplate jdbcTemplate) {
    this.jdbcTemplate = jdbcTemplate;
    }

    @Transactional
    public int[][] batchInsert(List<RebNumProtectDTO> content, int batchSize) {
        System.out.println("repository :: content :: " + content.size());

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
    public void updateMaestoComisionesSp(){
      /*  String sql = "call PPC_MIS_SP_REBAJA_MAESTRO_COMISIONES()";
        jdbcTemplate.execute(sql);
*/



        jdbcTemplate.setResultsMapCaseInsensitive(true);

        // Convert o_c_book SYS_REFCURSOR to List<Book>
        simpleJdbcCallRefCursor = new SimpleJdbcCall(jdbcTemplate)
                .withProcedureName("PPC_MIS_SP_REBAJA_MAESTRO_COMISIONES")
                .returningResultSet("elementos",
                        BeanPropertyRowMapper.newInstance(Integer.class));

        Map out = simpleJdbcCallRefCursor.execute();

        System.out.println("out :: >> " +  out.toString());

/*
        SimpleJdbcCall call = new SimpleJdbcCall(jdbcTemplate)
                .withProcedureName("PPC_MIS_REBAJA_PRONOSTICO");

        Map<String, Object> execute = call.execute();*/



    }

}

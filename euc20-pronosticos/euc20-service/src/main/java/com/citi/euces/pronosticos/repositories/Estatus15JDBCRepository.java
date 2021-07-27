package com.citi.euces.pronosticos.repositories;

import com.citi.euces.pronosticos.infra.dto.AddListFinalEstatus15DTO;
import com.citi.euces.pronosticos.infra.dto.AddListFinalTmpEstatus15DTO;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

@Repository
public class Estatus15JDBCRepository {
    private final JdbcTemplate jdbcTemplate;

    public Estatus15JDBCRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Transactional
    public void truncateTableEstatus15() {
        String query = "DELETE FROM PPC_MIS_ESTATUS15";
        jdbcTemplate.execute(query);
    }

    @Transactional
    public void truncateTableEstatus15tmp() {
        String query = "DELETE FROM PPC_MIS_ESTATUS15_TMP";
        jdbcTemplate.execute(query);
    }

    @Transactional
    public int[] batchInsert(List<AddListFinalEstatus15DTO> content) {
        int[] insertClients = this.jdbcTemplate.batchUpdate(
                "INSERT INTO PPC_MIS_ESTATUS15 (LLAVE, SEC_ARCH, SEC_INT, KEYUP) values(?,?,?,?)",
                new BatchPreparedStatementSetter() {
                    public void setValues(PreparedStatement ps, int i) throws SQLException {
                        ps.setString(1, content.get(i).getLlave());
                        ps.setInt(2, content.get(i).getSecArch());
                        ps.setInt(3, content.get(i).getSecInt());
                        ps.setString(4, content.get(i).getKeyUp());
                    }
                    public int getBatchSize() {
                        return content.size();
                    }
                });
        return insertClients;
    }
}
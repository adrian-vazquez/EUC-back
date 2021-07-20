package com.citi.euces.pronosticos.repositories;

import com.citi.euces.pronosticos.infra.dto.AddListFinalTmpEstatus15DTO;
import com.citi.euces.pronosticos.infra.dto.CatServiciosPronosticosDTO;
import com.citi.euces.pronosticos.infra.dto.Estatus15TmpDTO;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

@Repository
public class Estatus15TmpJDBCRepository {
    private final JdbcTemplate jdbcTemplate;

    public Estatus15TmpJDBCRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Transactional
    public Integer getLastSecE15() {
        String sql = "SELECT MAX(SEC_INT) FROM PPC_MIS_ESTATUS15_TMP";
        Integer sec_int = jdbcTemplate.queryForObject(sql, Integer.class);
        System.out.println("MAX-sec_int :: " + sec_int);
        return sec_int == null ? 1 : sec_int;
    }

    @Transactional
    public int[] batchInsert(List<AddListFinalTmpEstatus15DTO> content) {
        int[] insertClients = this.jdbcTemplate.batchUpdate(
                "INSERT INTO PPC_MIS_ESTATUS15_TMP (LLAVE, SEC_ARCH, SEC_INT, KEYUP, NO_CLIENTE, CTA_CLIENTE) values(?,?,?,?,?,?)",
                new BatchPreparedStatementSetter() {
                    public void setValues(PreparedStatement ps, int i) throws SQLException {
                        ps.setString(1, content.get(i).getLlave());
                        ps.setInt(2, content.get(i).getSecArch());
                        ps.setInt(3, content.get(i).getSecInt());
                        ps.setString(4, content.get(i).getKeyUp());
                        ps.setLong(5, content.get(i).getNoCliente());
                        ps.setLong(6, content.get(i).getCtaCliente());
                    }

                    public int getBatchSize() {
                        return content.size();
                    }
                });
        return insertClients;
    }

    @Transactional
    public List<Estatus15TmpDTO> findAll() {
        String sql = "SELECT LLAVE, SEC_INT, NO_CLIENTE SUCURSAL, CTA_CLIENTE CUENTA FROM PPC_MIS_ESTATUS15_TMP";
        return jdbcTemplate.query(sql, (rs, rowNum) ->
                new Estatus15TmpDTO(
                        rs.getString("LLAVE"),
                        rs.getLong("SEC_INT"),
                        rs.getLong("SUCURSAL"),
                        rs.getLong("CUENTA")
                )
        );

    }

}

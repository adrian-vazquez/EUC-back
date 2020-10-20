/**
 * 
 */
package com.citi.euces.pronosticos.infra.dto;

import java.io.Serializable;

/**
 * 
 * @author lbermejo
 *
 */
public class TableTO implements Serializable {
    private static final long serialVersionUID = 1L;

    private String table;
    private Long id;

    public TableTO() {

    }

    public TableTO(String table, Long id) {
        super();
        this.table = table;
        this.id = id;
    }

    public String getTable() {
        return table;
    }

    public void setTable(String table) {
        this.table = table;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

}

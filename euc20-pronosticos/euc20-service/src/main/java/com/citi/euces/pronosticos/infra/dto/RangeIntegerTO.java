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
public class RangeIntegerTO implements Serializable {

    private static final long serialVersionUID = 1L;
    private Integer init;
    private Integer end;

    public RangeIntegerTO() {
    }

    public RangeIntegerTO(Integer init, Integer end) {
        super();
        this.init = init;
        this.end = end;
    }

    public Integer getInit() {
        return init;
    }

    public void setInit(Integer init) {
        this.init = init;
    }

    public Integer getEnd() {
        return end;
    }

    public void setEnd(Integer end) {
        this.end = end;
    }

    @Override
    public String toString() {
        return "RangeIntegerTO [init=" + init + ", end=" + end + "]";
    }

}

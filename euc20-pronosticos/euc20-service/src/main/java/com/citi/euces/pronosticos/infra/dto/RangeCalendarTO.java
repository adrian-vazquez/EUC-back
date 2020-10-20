/**
 * 
 */
package com.citi.euces.pronosticos.infra.dto;

import java.io.Serializable;
import java.util.Calendar;

/**
 * 
 * @author lbermejo
 *
 */
public class RangeCalendarTO implements Serializable {

    private static final long serialVersionUID = 1L;
    private Calendar init;
    private Calendar end;

    public RangeCalendarTO() {
        this.init = Calendar.getInstance();
        this.end = Calendar.getInstance();
    }

    public RangeCalendarTO(Calendar init, Calendar end) {
        super();
        this.init = init;
        this.end = end;
    }

    public Calendar getInit() {
        return init;
    }

    public void setInit(Calendar init) {
        this.init = init;
    }

    public Calendar getEnd() {
        return end;
    }

    public void setEnd(Calendar end) {
        this.end = end;
    }

    @Override
    public String toString() {
        return "RangeCalendarTO [init=" + init + ", end=" + end + "]";
    }

}

package com.citi.euces.pronosticos.models;

import java.io.Serializable;

public class BtnSaveRequest implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Integer mes1;
	private Integer anio1;
	private Integer dias1;
	private Integer mes2;
	private Integer anio2;
	private Integer dias2;
	private Integer mes3;
	private Integer anio3;
	private Integer dias3;
	private String operacion;
	private Integer secuencial;
	private Integer dias;
	
	public Integer getMes1() {
		return mes1;
	}
	public void setMes1(Integer mes1) {
		this.mes1 = mes1;
	}
	public Integer getAnio1() {
		return anio1;
	}
	public void setAnio1(Integer anio1) {
		this.anio1 = anio1;
	}
	public Integer getDias1() {
		return dias1;
	}
	public void setDias1(Integer dias1) {
		this.dias1 = dias1;
	}
	public Integer getMes2() {
		return mes2;
	}
	public void setMes2(Integer mes2) {
		this.mes2 = mes2;
	}
	public Integer getAnio2() {
		return anio2;
	}
	public void setAnio2(Integer anio2) {
		this.anio2 = anio2;
	}
	public Integer getDias2() {
		return dias2;
	}
	public void setDias2(Integer dias2) {
		this.dias2 = dias2;
	}
	public Integer getMes3() {
		return mes3;
	}
	public void setMes3(Integer mes3) {
		this.mes3 = mes3;
	}
	public Integer getAnio3() {
		return anio3;
	}
	public void setAnio3(Integer anio3) {
		this.anio3 = anio3;
	}
	public Integer getDias3() {
		return dias3;
	}
	public void setDias3(Integer dias3) {
		this.dias3 = dias3;
	}
	public String getOperacion() {
		return operacion;
	}
	public void setOperacion(String operacion) {
		this.operacion = operacion;
	}
	public Integer getSecuencial() {
		return secuencial;
	}
	public void setSecuencial(Integer secuencial) {
		this.secuencial = secuencial;
	}
	public Integer getDias() {
		return dias;
	}
	public void setDias(Integer dias) {
		this.dias = dias;
	}
	
	
}

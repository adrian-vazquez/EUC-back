package com.citi.euces.pronosticos.infra.dto;

import java.io.Serializable;

public class QueryCtosAgrupadoDTO implements Serializable{
	
	private Long cuenta;
	private Integer prefmda;
	private Integer cuentamda;
	private Integer cveEstatus;
	private String nombre;
	private Integer uso;
	private Integer mon;
	private Integer franquicia;
	private Integer duplicado;
	private Integer id;
	
	public Long getCuenta() {
		return cuenta;
	}
	public void setCuenta(Long cuenta) {
		this.cuenta = cuenta;
	}
	public Integer getPrefmda() {
		return prefmda;
	}
	public void setPrefmda(Integer prefmda) {
		this.prefmda = prefmda;
	}
	public Integer getCuentamda() {
		return cuentamda;
	}
	public void setCuentamda(Integer cuentamda) {
		this.cuentamda = cuentamda;
	}
	public Integer getCveEstatus() {
		return cveEstatus;
	}
	public void setCveEstatus(Integer cveEstatus) {
		this.cveEstatus = cveEstatus;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public Integer getUso() {
		return uso;
	}
	public void setUso(Integer uso) {
		this.uso = uso;
	}
	public Integer getMon() {
		return mon;
	}
	public void setMon(Integer mon) {
		this.mon = mon;
	}
	public Integer getFranquicia() {
		return franquicia;
	}
	public void setFranquicia(Integer franquicia) {
		this.franquicia = franquicia;
	}
	public Integer getDuplicado() {
		return duplicado;
	}
	public void setDuplicado(Integer duplicado) {
		this.duplicado = duplicado;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	
	
}

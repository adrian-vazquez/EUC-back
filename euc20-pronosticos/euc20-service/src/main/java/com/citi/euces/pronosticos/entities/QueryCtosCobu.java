package com.citi.euces.pronosticos.entities;

import java.io.Serializable;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "PPC_PCB_QUERY_CTOS_COBU")
public class QueryCtosCobu implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Column(name = "CUENTA")
	public Long cuenta;
	
	@Column(name = "PREFMDA")
	private String prefmda;
	
	@Column(name = "CUENTAMDA")
	private String cuentamda;
	
	@Column(name = "CVE_ESTATUS")
	private String cveEstatus;
	
	@Column(name = "NOMBRE")
	private String nombre;
	
	@Column(name = "USO")
	private String uso;
	
	@Column(name = "MON")
	private String mon;
	
	@Column(name = "ESTATUS")
	private Integer estatus;
	
	@Column(name = "PROD")
	private Integer prod;
	
	@Column(name = "INST")
	private Integer inst;
	
	@Column(name = "FRANQUICIA")
	private Integer franquicia;
	
	@Id
	@Basic(optional = false)
	@NotNull
	@Column(name = "ID")
	private Long id;

	public Long getCuenta() {
		return cuenta;
	}

	public void setCuenta(Long cuenta) {
		this.cuenta = cuenta;
	}

	public String getPrefmda() {
		return prefmda;
	}

	public void setPrefmda(String prefmda) {
		this.prefmda = prefmda;
	}

	public String getCuentamda() {
		return cuentamda;
	}

	public void setCuentamda(String cuentamda) {
		this.cuentamda = cuentamda;
	}

	public String getCveEstatus() {
		return cveEstatus;
	}

	public void setCveEstatus(String cveEstatus) {
		this.cveEstatus = cveEstatus;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getUso() {
		return uso;
	}

	public void setUso(String uso) {
		this.uso = uso;
	}

	public String getMon() {
		return mon;
	}

	public void setMon(String mon) {
		this.mon = mon;
	}

	public Integer getEstatus() {
		return estatus;
	}

	public void setEstatus(Integer estatus) {
		this.estatus = estatus;
	}

	public Integer getProd() {
		return prod;
	}

	public void setProd(Integer prod) {
		this.prod = prod;
	}

	public Integer getInst() {
		return inst;
	}

	public void setInst(Integer inst) {
		this.inst = inst;
	}

	public Integer getFranquicia() {
		return franquicia;
	}

	public void setFranquicia(Integer franquicia) {
		this.franquicia = franquicia;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	
}

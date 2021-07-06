package com.citi.euces.pronosticos.entities;

import java.io.Serializable;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "PPC_PCB_QUERY_CTOS_DUPLICADOS")
public class QueryCtosDuplicados implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Column(name = "CUENTA")
	private Long cuenta;
	
	@Column(name = "CTA_CUENTA")
	private Long ctaCuenta;
	
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

	public Long getCtaCuenta() {
		return ctaCuenta;
	}

	public void setCtaCuenta(Long ctaCuenta) {
		this.ctaCuenta = ctaCuenta;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	
}

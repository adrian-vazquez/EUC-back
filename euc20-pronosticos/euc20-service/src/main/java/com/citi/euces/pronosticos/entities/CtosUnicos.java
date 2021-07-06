package com.citi.euces.pronosticos.entities;

import java.io.Serializable;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name  = "PPC_PCB_CTOS_UNICOS")
public class CtosUnicos implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Column(name = "NUM_CUENTA")
	private Long numCuenta;
	
	@Column(name = "SUC")
	private Integer suc;
	
	@Column(name = "CTA")
	private Long cta;
	
	@Column(name = "MON")
	private Double mon;
	
	@Column(name = "FRANQUICIA")
	private Integer franquicia;
	
	@Column(name = "USO")
	private Double uso;
	
	@Id
	@Basic(optional = false)
	@NotNull
	@Column(name = "ID")
	private Long id;

	public Long getNumCuenta() {
		return numCuenta;
	}

	public void setNumCuenta(Long numCuenta) {
		this.numCuenta = numCuenta;
	}

	public Integer getSuc() {
		return suc;
	}

	public void setSuc(Integer suc) {
		this.suc = suc;
	}

	public Long getCta() {
		return cta;
	}

	public void setCta(Long cta) {
		this.cta = cta;
	}

	public Double getMon() {
		return mon;
	}

	public void setMon(Double mon) {
		this.mon = mon;
	}

	public Integer getFranquicia() {
		return franquicia;
	}

	public void setFranquicia(Integer franquicia) {
		this.franquicia = franquicia;
	}

	public Double getUso() {
		return uso;
	}

	public void setUso(Double uso) {
		this.uso = uso;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	
}

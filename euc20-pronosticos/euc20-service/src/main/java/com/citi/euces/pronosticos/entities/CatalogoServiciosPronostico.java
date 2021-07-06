package com.citi.euces.pronosticos.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
//import javax.persistence.GeneratedValue;
//import javax.persistence.GenerationType;
//import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "PPC_MIS_CATALOGO_SERVICIOS_PRONOSTICO")
public class CatalogoServiciosPronostico implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id @GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "ID_SERVICIO")
	private Integer idServicio;
	@Column(name = "ID_ONDEMAND")
	private Integer idOndemand;
	@Column(name = "SERVICIO")
	private String servicio;
	
	public CatalogoServiciosPronostico(){}

	public CatalogoServiciosPronostico(Integer idServicio, Integer idOndemand, String servicio) {
		super();
		this.idServicio = idServicio;
		this.idOndemand = idOndemand;
		this.servicio = servicio;
	}

	/**
	 * @return the idServicio
	 */
	public Integer getIdServicio() {
		return idServicio;
	}

	/**
	 * @param idServicio the idServicio to set
	 */
	public void setIdServicio(Integer idServicio) {
		this.idServicio = idServicio;
	}

	/**
	 * @return the idOndemand
	 */
	public Integer getIdOndemand() {
		return idOndemand;
	}

	/**
	 * @param idOndemand the idOndemand to set
	 */
	public void setIdOndemand(Integer idOndemand) {
		this.idOndemand = idOndemand;
	}

	/**
	 * @return the servicio
	 */
	public String getServicio() {
		return servicio;
	}

	/**
	 * @param servicio the servicio to set
	 */
	public void setServicio(String servicio) {
		this.servicio = servicio;
	}
	
	
}

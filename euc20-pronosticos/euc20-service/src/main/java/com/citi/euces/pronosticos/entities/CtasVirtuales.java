package com.citi.euces.pronosticos.entities;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name = "PPC_PCB_CTAS_VIRTUALES")
public class CtasVirtuales implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Column(name = "NUM_CLIENTE")
	private Long numCliente;
	
	@Column(name = "NUM_CUENTA")
	private Long numCuenta;
	
	@Column(name = "FEC_ALTA")
	private Date fecAlta;
	
	@Column(name = "CUENTAS_X")
	private Long cuentasX;
	
	@Size(min = 1, max = 55)
	@Column(name = "NOMBRE")
	private String nombre;
	
	@Column(name = "MES")
	private Integer mes;
	
	@Column(name = "ANIO")
	private Integer anio;
	
	@Id
	@GeneratedValue
	@Basic(optional = false)
	@NotNull
	@Column(name = "ID")
	private Long id;

	public Long getNumCliente() {
		return numCliente;
	}

	public void setNumCliente(Long numCliente) {
		this.numCliente = numCliente;
	}

	public Long getNumCuenta() {
		return numCuenta;
	}

	public void setNumCuenta(Long numCuenta) {
		this.numCuenta = numCuenta;
	}

	public Date getFecAlta() {
		return fecAlta;
	}

	public void setFecAlta(Date fecAlta) {
		this.fecAlta = fecAlta;
	}

	public Long getCuentaX() {
		return cuentasX;
	}

	public void setCuentaX(Long cuentaX) {
		this.cuentasX = cuentaX;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public Integer getMes() {
		return mes;
	}

	public void setMes(Integer mes) {
		this.mes = mes;
	}

	public Integer getAnio() {
		return anio;
	}

	public void setAnio(Integer anio) {
		this.anio = anio;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	
}

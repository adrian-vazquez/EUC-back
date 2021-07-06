package com.citi.euces.pronosticos.entities;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity 
@Table(name = "PPC_PCB_TXS_CTAS_VIRT")
public class TxsCtasVirt implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Column(name = "NUM_CLIENTE")
	private Long numCliente;

	@Column(name = "NUM_CTA")
	private Long numCuenta;
	
	@Size(min = 1, max = 55)
	@Column(name = "CTE_ALIAS")
	private String cteAlias;
	
	@Size(min = 1, max = 55)
	@Column(name = "NOMBRE")
	private String nombre;
	
	@Column(name = "CVE_MON_SISTEMA")
	private Integer cveMonSistema;
	
	@Column(name = "FEC_INFORMACION")
	private Date fechaInformacion;
	
	@Column(name = "NUM_MED_ACCESO")
	private Double numMedAcceso;
	
	@Column(name = "CVE_TXNSISTEMA")
	private Integer cveTxnSistema;

	@Column(name = "NUM_SUC_PROMTORMDA")
	private Integer numSucursal;
	
	@Column(name = "IMP_TRANSACCION")
	private Double ImpTransaccion;
	
	@Column(name = "NUM_AUT_TRANS")
	private Double numAutTrans;
	
	@Column(name = "NUM_SUC_OPERADORA")
	private Integer numSucOperadora;
	
	@Size(min = 1, max = 20)
	@Column(name = "TIPO")
	private String tipo;
	
	@Id
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

	public String getCteAlias() {
		return cteAlias;
	}

	public void setCteAlias(String cteAlias) {
		this.cteAlias = cteAlias;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public Integer getCveMonSistema() {
		return cveMonSistema;
	}

	public void setCveMonSistema(Integer cveMonSistema) {
		this.cveMonSistema = cveMonSistema;
	}

	public Date getFechaInformacion() {
		return fechaInformacion;
	}

	public void setFechaInformacion(Date fechaInformacion) {
		this.fechaInformacion = fechaInformacion;
	}

	public Double getNumMedAcceso() {
		return numMedAcceso;
	}

	public void setNumMedAcceso(Double numMedAcceso) {
		this.numMedAcceso = numMedAcceso;
	}

	public Integer getCveTxnSistema() {
		return cveTxnSistema;
	}

	public void setCveTxnSistema(Integer cveTxnSistema) {
		this.cveTxnSistema = cveTxnSistema;
	}

	public Integer getNumSucursal() {
		return numSucursal;
	}

	public void setNumSucursal(Integer numSucursal) {
		this.numSucursal = numSucursal;
	}

	public Double getImpTransaccion() {
		return ImpTransaccion;
	}

	public void setImpTransaccion(Double impTransaccion) {
		ImpTransaccion = impTransaccion;
	}

	public Double getNumAutTrans() {
		return numAutTrans;
	}

	public void setNumAutTrans(Double numAutTrans) {
		this.numAutTrans = numAutTrans;
	}

	public Integer getNumSucOperadora() {
		return numSucOperadora;
	}

	public void setNumSucOperadora(Integer numSucOperadora) {
		this.numSucOperadora = numSucOperadora;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	
}










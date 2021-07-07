package com.citi.euces.pronosticos.infra.dto;

import java.io.Serializable;
import java.util.Date;

import org.apache.poi.xssf.usermodel.XSSFCell;

public class TxsCtasVirtDTO implements Serializable{

	private Long numCliente;
	private Long numCta;
	private String cteAlias;
	private String nombre;
	private Integer cveMonSistema;
	private Date fecInformacion;
	private Double numMedAcceso;
	private Integer cveTxnSistema;
	private Integer numSucPromtormda;
	private Double impTransaccion;
	private Double numAutTrans;
	private Integer numSucOperadora;
	private Integer tipo;
	private Integer id;
	
	public Long getNumCliente() {
		return numCliente;
	}
	public void setNumCliente(Long numCliente) {
		this.numCliente = numCliente;
	}
	public Long getNumCta() {
		return numCta;
	}
	public void setNumCta(Long numCta) {
		this.numCta = numCta;
	}
	public String getCteAlias() {
		return cteAlias;
	}
	public void setCteAlias(String string) {
		this.cteAlias = string;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public Integer getCveMonSstema() {
		return cveMonSistema;
	}
	public void setCveMonSstema(Integer cveMonSstema) {
		this.cveMonSistema = cveMonSstema;
	}
	public Date getFecInformacion() {
		return fecInformacion;
	}
	public void setFecInformacion(Date fecInformacion) {
		this.fecInformacion = fecInformacion;
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
	public Integer getNumSucPromtormda() {
		return numSucPromtormda;
	}
	public void setNumSucPromtormda(Integer numSucPromtormda) {
		this.numSucPromtormda = numSucPromtormda;
	}
	public Double getImpTransaccion() {
		return impTransaccion;
	}
	public void setImpTransaccion(Double impTransaccion) {
		this.impTransaccion = impTransaccion;
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
	public Integer getTipo() {
		return tipo;
	}
	public void setTipo(Integer tipo) {
		this.tipo = tipo;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	
	
}

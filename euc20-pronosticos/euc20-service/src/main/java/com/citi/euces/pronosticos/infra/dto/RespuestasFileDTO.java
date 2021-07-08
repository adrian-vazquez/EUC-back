package com.citi.euces.pronosticos.infra.dto;

import java.io.Serializable;
import java.util.Date;

public class RespuestasFileDTO implements Serializable {

	private Integer noCliente;
	private String ctaCliente;
	private String contrato;
	private Double impOperacion1;
	private Double impOperacion2;
	private String codOperacion;
	private String descRechazo;
	private String leyendaEmisor;
	private Date fecOperacion;
	private String numProteccion;
	private Integer secuencial;
	private Date fecReal;
	private String franquicia;
	private Date fecVencimiento;
	private Integer secArc;
	private Integer secInt;
	private String nomFranquicia;
	
	public Integer getNoCliente() {
		return noCliente;
	}
	public void setNoCliente(Integer noCliente) {
		this.noCliente = noCliente;
	}
	public String getCtaCliente() {
		return ctaCliente;
	}
	public void setCtaCliente(String ctaCliente) {
		this.ctaCliente = ctaCliente;
	}
	public String getContrato() {
		return contrato;
	}
	public void setContrato(String contrato) {
		this.contrato = contrato;
	}
	public Double getImpOperacion1() {
		return impOperacion1;
	}
	public void setImpOperacion1(Double impOperacion1) {
		this.impOperacion1 = impOperacion1;
	}
	public Double getImpOperacion2() {
		return impOperacion2;
	}
	public void setImpOperacion2(Double impOperacion2) {
		this.impOperacion2 = impOperacion2;
	}
	public String getCodOperacion() {
		return codOperacion;
	}
	public void setCodOperacion(String codOperacion) {
		this.codOperacion = codOperacion;
	}
	public String getDescRechazo() {
		return descRechazo;
	}
	public void setDescRechazo(String descRechazo) {
		this.descRechazo = descRechazo;
	}
	public String getLeyendaEmisor() {
		return leyendaEmisor;
	}
	public void setLeyendaEmisor(String leyendaEmisor) {
		this.leyendaEmisor = leyendaEmisor;
	}
	public Date getFecOperacion() {
		return fecOperacion;
	}
	public void setFecOperacion(Date fecOperacion) {
		this.fecOperacion = fecOperacion;
	}
	public String getNumProteccion() {
		return numProteccion;
	}
	public void setNumProteccion(String numProteccion) {
		this.numProteccion = numProteccion;
	}
	public Integer getSecuencial() {
		return secuencial;
	}
	public void setSecuencial(Integer secuencial) {
		this.secuencial = secuencial;
	}
	public Date getFecReal() {
		return fecReal;
	}
	public void setFecReal(Date fecReal) {
		this.fecReal = fecReal;
	}
	public String getFranquicia() {
		return franquicia;
	}
	public void setFranquicia(String franquicia) {
		this.franquicia = franquicia;
	}
	public Date getFecVencimiento() {
		return fecVencimiento;
	}
	public void setFecVencimiento(Date fecVencimiento) {
		this.fecVencimiento = fecVencimiento;
	}
	public Integer getSecArc() {
		return secArc;
	}
	public void setSecArc(Integer secArc) {
		this.secArc = secArc;
	}
	public Integer getSecInt() {
		return secInt;
	}
	public void setSecInt(Integer secInt) {
		this.secInt = secInt;
	}
	public String getNomFranquicia() {
		return nomFranquicia;
	}
	public void setNomFranquicia(String nomFranquicia) {
		this.nomFranquicia = nomFranquicia;
	}
	
	
}

package com.citi.euces.pronosticos.infra.dto;

import java.io.Serializable;
import java.util.Date;

public class SubirRespuestaDTO implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private String tipoRegistro;
	private long numSecuencia;
	private String codOperacion;
	private String codDivisa;
	private double importeOperacion;
	private Date fecAplicacion;
	private String periodoProteccion;
	private String numCliente;
	private String tipoOperacion;
	private Date fecVencimiento;
	private String bancoReceptor;
	private String tipoCtaCliente;
	private String ctaCliente;
	private String nomCliente;
	private String refEmisor;
	private String usoFuturo1;
	private String usoFuturo2;
	private String usoFuturo3;
	private String usoFuturo4;
	private String usoFuturo5;
	private String titularServicio;
	private double saldoProteccion;
	private String refNumEmisor;
	private String refLeyendaEmisor;
	private String motivoRechazo;
	private String numProteccion;
	private String inicioProteccion;
	private String secArcOriginal;
	private String referencia1;
	private String referencia2;
	private String referencia3UsuFuturo;
	private String descRechazo;
	private String cntrClienteUsr;
	private String concepto;
	private String subConcepto;
	private long numMesCobro;
	private long secRegOriginal;
	private String ctaAbono;
	private String tipoCtaAbono;
	private String conceptoCobro2;
	private String subconceptoCobro2;
	private double importe2;
	private long id;
		
	public String getTipoRegistro() {
		return tipoRegistro;
	}
	public void setTipoRegistro(String tipoRegistro) {
		this.tipoRegistro = tipoRegistro;
	}
	public long getNumSecuencia() {
		return numSecuencia;
	}
	public void setNumSecuencia(long numSecuencia) {
		this.numSecuencia = numSecuencia;
	}
	public String getCodOperacion() {
		return codOperacion;
	}
	public void setCodOperacion(String codOperacion) {
		this.codOperacion = codOperacion;
	}
	public String getCodDivisa() {
		return codDivisa;
	}
	public void setCodDivisa(String codDivisa) {
		this.codDivisa = codDivisa;
	}
	public double getImporteOperacion() {
		return importeOperacion;
	}
	public void setImporteOperacion(double importeOperacion) {
		this.importeOperacion = importeOperacion;
	}
	public Date getFecAplicacion() {
		return fecAplicacion;
	}
	public void setFecAplicacion(Date fecAplicacion) {
		this.fecAplicacion = fecAplicacion;
	}
	public String getPeriodoProteccion() {
		return periodoProteccion;
	}
	public void setPeriodoProteccion(String periodoProteccion) {
		this.periodoProteccion = periodoProteccion;
	}
	public String getNumCliente() {
		return numCliente;
	}
	public void setNumCliente(String numCliente) {
		this.numCliente = numCliente;
	}
	public String getTipoOperacion() {
		return tipoOperacion;
	}
	public void setTipoOperacion(String tipoOperacion) {
		this.tipoOperacion = tipoOperacion;
	}
	public Date getFecVencimiento() {
		return fecVencimiento;
	}
	public void setFecVencimiento(Date fecVencimiento) {
		this.fecVencimiento = fecVencimiento;
	}
	public String getBancoReceptor() {
		return bancoReceptor;
	}
	public void setBancoReceptor(String bancoReceptor) {
		this.bancoReceptor = bancoReceptor;
	}
	public String getTipoCtaCliente() {
		return tipoCtaCliente;
	}
	public void setTipoCtaCliente(String tipoCtaCliente) {
		this.tipoCtaCliente = tipoCtaCliente;
	}
	public String getCtaCliente() {
		return ctaCliente;
	}
	public void setCtaCliente(String ctaCliente) {
		this.ctaCliente = ctaCliente;
	}
	public String getNomCliente() {
		return nomCliente;
	}
	public void setNomCliente(String nomCliente) {
		this.nomCliente = nomCliente;
	}
	public String getRefEmisor() {
		return refEmisor;
	}
	public void setRefEmisor(String refEmisor) {
		this.refEmisor = refEmisor;
	}
	public String getUsoFuturo1() {
		return usoFuturo1;
	}
	public void setUsoFuturo1(String usoFuturo1) {
		this.usoFuturo1 = usoFuturo1;
	}
	public String getUsoFuturo2() {
		return usoFuturo2;
	}
	public void setUsoFuturo2(String usoFuturo2) {
		this.usoFuturo2 = usoFuturo2;
	}
	public String getUsoFuturo3() {
		return usoFuturo3;
	}
	public void setUsoFuturo3(String usoFuturo3) {
		this.usoFuturo3 = usoFuturo3;
	}
	public String getUsoFuturo4() {
		return usoFuturo4;
	}
	public void setUsoFuturo4(String usoFuturo4) {
		this.usoFuturo4 = usoFuturo4;
	}
	public String getUsoFuturo5() {
		return usoFuturo5;
	}
	public void setUsoFuturo5(String usoFuturo5) {
		this.usoFuturo5 = usoFuturo5;
	}
	public String getTitularServicio() {
		return titularServicio;
	}
	public void setTitularServicio(String titularServicio) {
		this.titularServicio = titularServicio;
	}
	public double getSaldoProteccion() {
		return saldoProteccion;
	}
	public void setSaldoProteccion(double saldoProteccion) {
		this.saldoProteccion = saldoProteccion;
	}
	public String getRefNumEmisor() {
		return refNumEmisor;
	}
	public void setRefNumEmisor(String refNumEmisor) {
		this.refNumEmisor = refNumEmisor;
	}
	public String getRefLeyendaEmisor() {
		return refLeyendaEmisor;
	}
	public void setRefLeyendaEmisor(String refLeyendaEmisor) {
		this.refLeyendaEmisor = refLeyendaEmisor;
	}
	public String getMotivoRechazo() {
		return motivoRechazo;
	}
	public void setMotivoRechazo(String motivoRechazo) {
		this.motivoRechazo = motivoRechazo;
	}
	public String getNumProteccion() {
		return numProteccion;
	}
	public void setNumProteccion(String numProteccion) {
		this.numProteccion = numProteccion;
	}
	public String getInicioProteccion() {
		return inicioProteccion;
	}
	public void setInicioProteccion(String inicioProteccion) {
		this.inicioProteccion = inicioProteccion;
	}
	public String getSecArcOriginal() {
		return secArcOriginal;
	}
	public void setSecArcOriginal(String secArcOriginal) {
		this.secArcOriginal = secArcOriginal;
	}
	public String getReferencia1() {
		return referencia1;
	}
	public void setReferencia1(String referencia1) {
		this.referencia1 = referencia1;
	}
	public String getReferencia2() {
		return referencia2;
	}
	public void setReferencia2(String referencia2) {
		this.referencia2 = referencia2;
	}
	public String getReferencia3UsuFuturo() {
		return referencia3UsuFuturo;
	}
	public void setReferencia3UsuFuturo(String referencia3UsuFuturo) {
		this.referencia3UsuFuturo = referencia3UsuFuturo;
	}
	public String getDescRechazo() {
		return descRechazo;
	}
	public void setDescRechazo(String descRechazo) {
		this.descRechazo = descRechazo;
	}
	public String getCntrClienteUsr() {
		return cntrClienteUsr;
	}
	public void setCntrClienteUsr(String cntrClienteUsr) {
		this.cntrClienteUsr = cntrClienteUsr;
	}
	public String getConcepto() {
		return concepto;
	}
	public void setConcepto(String concepto) {
		this.concepto = concepto;
	}
	public String getSubConcepto() {
		return subConcepto;
	}
	public void setSubConcepto(String subConcepto) {
		this.subConcepto = subConcepto;
	}
	public long getNumMesCobro() {
		return numMesCobro;
	}
	public void setNumMesCobro(long numMesCobro) {
		this.numMesCobro = numMesCobro;
	}
	public long getSecRegOriginal() {
		return secRegOriginal;
	}
	public void setSecRegOriginal(long secRegOriginal) {
		this.secRegOriginal = secRegOriginal;
	}
	public String getCtaAbono() {
		return ctaAbono;
	}
	public void setCtaAbono(String ctaAbono) {
		this.ctaAbono = ctaAbono;
	}
	public String getTipoCtaAbono() {
		return tipoCtaAbono;
	}
	public void setTipoCtaAbono(String tipoCtaAbono) {
		this.tipoCtaAbono = tipoCtaAbono;
	}
	public String getConceptoCobro2() {
		return conceptoCobro2;
	}
	public void setConceptoCobro2(String conceptoCobro2) {
		this.conceptoCobro2 = conceptoCobro2;
	}
	public String getSubconceptoCobro2() {
		return subconceptoCobro2;
	}
	public void setSubconceptoCobro2(String subconceptoCobro2) {
		this.subconceptoCobro2 = subconceptoCobro2;
	}
	public double getImporte2() {
		return importe2;
	}
	public void setImporte2(double importe2) {
		this.importe2 = importe2;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
}
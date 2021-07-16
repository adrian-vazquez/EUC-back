package com.citi.euces.pronosticos.infra.dto;

import java.io.Serializable;

public class RepoLayoutPrevioDTO implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private String fecCarga;
	private String ctratoTelCta;
	private String ctaOrigen;
	private String cantidad;
	private String emisor;
	private String dias;
	private String ctaAbono;
	private String concepto;
	private String refEmisor;
	private String leyenda;
	
	public String getFecCarga() {
		return fecCarga;
	}
	public void setFecCarga(String fecCarga) {
		this.fecCarga = fecCarga;
	}
	public String getCtratoTelCta() {
		return ctratoTelCta;
	}
	public void setCtratoTelCta(String ctratoTelCta) {
		this.ctratoTelCta = ctratoTelCta;
	}
	public String getCtaOrigen() {
		return ctaOrigen;
	}
	public void setCtaOrigen(String ctaOrigen) {
		this.ctaOrigen = ctaOrigen;
	}
	public String getCantidad() {
		return cantidad;
	}
	public void setCantidad(String cantidad) {
		this.cantidad = cantidad;
	}
	public String getEmisor() {
		return emisor;
	}
	public void setEmisor(String emisor) {
		this.emisor = emisor;
	}
	public String getDias() {
		return dias;
	}
	public void setDias(String dias) {
		this.dias = dias;
	}
	public String getCtaAbono() {
		return ctaAbono;
	}
	public void setCtaAbono(String ctaAbono) {
		this.ctaAbono = ctaAbono;
	}
	public String getConcepto() {
		return concepto;
	}
	public void setConcepto(String concepto) {
		this.concepto = concepto;
	}
	public String getRefEmisor() {
		return refEmisor;
	}
	public void setRefEmisor(String refEmisor) {
		this.refEmisor = refEmisor;
	}
	public String getLeyenda() {
		return leyenda;
	}
	public void setLeyenda(String leyenda) {
		this.leyenda = leyenda;
	}	

}

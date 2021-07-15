package com.citi.euces.pronosticos.infra.dto;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;

public class LayoutPrevioDTO implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	@Temporal(TemporalType.TIMESTAMP)
	private String fecCarga;
	private String ctratoTelCta;
	private String ctaOrigen;
	private Double cantidad;
	private String emisor;
	private Integer dias;
	private String ctaAbono;
	private String concepto;
	private String refEmisor;
	private String leyenda;

	public LayoutPrevioDTO() {

	}

    public LayoutPrevioDTO(String fecCarga, String ctratoTelCta, String ctaOrigen, Double cantidad, String emisor,
			Integer dias, String ctaAbono, String concepto, String refEmisor, String leyenda) {
		super();
		this.fecCarga = fecCarga;
		this.ctratoTelCta = ctratoTelCta;
		this.ctaOrigen = ctaOrigen;
		this.cantidad = cantidad;
		this.emisor = emisor;
		this.dias = dias;
		this.ctaAbono = ctaAbono;
		this.concepto = concepto;
		this.refEmisor = refEmisor;
		this.leyenda = leyenda;
	}
	
	
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



	public Double getCantidad() {
		return cantidad;
	}



	public void setCantidad(Double cantidad) {
		this.cantidad = cantidad;
	}



	public String getEmisor() {
		return emisor;
	}



	public void setEmisor(String emisor) {
		this.emisor = emisor;
	}



	public Integer getDias() {
		return dias;
	}



	public void setDias(Integer dias) {
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



	@Override
	public String toString() {
		return "LayouPrevioDTO [fecCarga=" + fecCarga + ", ctratoTelCta=" + ctratoTelCta + ", ctaOrigen=" + ctaOrigen
				+ ", cantidad=" + cantidad + ", emisor=" + emisor + ", dias=" + dias + ", ctaAbono=" + ctaAbono
				+ ", concepto=" + concepto + ", refEmisor=" + refEmisor + ", leyenda=" + leyenda + "]";
	}
	
	
}
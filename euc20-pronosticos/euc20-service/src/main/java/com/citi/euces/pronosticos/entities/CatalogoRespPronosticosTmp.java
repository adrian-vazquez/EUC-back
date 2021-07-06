package com.citi.euces.pronosticos.entities;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "PPC_MIS_RESP_PRONOSTICOS_TMP")
public class CatalogoRespPronosticosTmp implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id @GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "NO_CLIENTE")
	private Integer noCliente;
	@Column(name = "CTA_CLIENTE")
	private Integer ctaCliente;
	@Column(name = "CONTRATO")
	private String contrato;
	@Column(name = "IMP_OPERACION_1")
	private Double impOperacion1;
	@Column(name = "IMP_OPERACION_2")
	private Double impOperacion2;
	@Column(name = "COD_OPERACION")
	private String codOperacion;
	@Column(name = "DESC_RECHAZO")
	private String descRechazo;
	@Column(name = "LEYENDA_EMISOR")
	private String leyendaEmisor;
	@Column(name = "FEC_OPERACION")
	@Temporal(TemporalType.DATE)
	private Date fecOperacion;
	@Column(name = "NUM_PROTECCION")
	private String numProteccion;
	@Column(name = "SECUENCIAL")
	private Integer secuencial;
	@Column(name = "FEC_REAL")
	private Date fecReal;
	@Column(name = "FRANQUICIA")
	private String franquicia;
	@Column(name = "FEC_VENCIMIENTO")
	@Temporal(TemporalType.DATE)
	private Date fecVencimiento;
	@Column(name = "SEC_ARC")
	private Integer secArc;
	@Column(name = "SEC_INT")
	private Integer secInt;
	@Column(name = "NOM_FRANQUICIA")
	private String nomFranquicia;
	
	public CatalogoRespPronosticosTmp() {}
	
	public CatalogoRespPronosticosTmp(Integer noCliente, Integer ctaCliente, String contrato, Double impOperacion1,
			Double impOperacion2, String codOperacion, String descRechazo, String leyendaEmisor, Date fecOperacion,
			String numProteccion, Integer secuencial, Date fecReal, String franquicia, Date fecVencimiento,
			Integer secArc, Integer secInt, String nomFranquicia) {
		super();
		this.noCliente = noCliente;
		this.ctaCliente = ctaCliente;
		this.contrato = contrato;
		this.impOperacion1 = impOperacion1;
		this.impOperacion2 = impOperacion2;
		this.codOperacion = codOperacion;
		this.descRechazo = descRechazo;
		this.leyendaEmisor = leyendaEmisor;
		this.fecOperacion = fecOperacion;
		this.numProteccion = numProteccion;
		this.secuencial = secuencial;
		this.fecReal = fecReal;
		this.franquicia = franquicia;
		this.fecVencimiento = fecVencimiento;
		this.secArc = secArc;
		this.secInt = secInt;
		this.nomFranquicia = nomFranquicia;
	}

	public Integer getNoCliente() {
		return noCliente;
	}

	public void setNoCliente(Integer noCliente) {
		this.noCliente = noCliente;
	}

	public Integer getCtaCliente() {
		return ctaCliente;
	}

	public void setCtaCliente(Integer ctaCliente) {
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

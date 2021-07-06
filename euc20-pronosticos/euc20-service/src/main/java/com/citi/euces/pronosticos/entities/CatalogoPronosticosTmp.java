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
@Table(name = "PPC_MIS_PRONOSTICOS_TMP")
public class CatalogoPronosticosTmp implements Serializable {
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id @GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "ID")
	private Integer id;
	@Column(name = "CLIENTE")
	private Integer cliente;
	@Column(name = "BLANCO")
	private String blanco;
	@Column(name = "IMPORTE")
	private Double importe;
	@Column(name = "IVA")
	private Integer iva;
	@Column(name = "STATUS")
	private String status;
	@Column(name = "MES")
	private String mes;
	@Column(name = "ANIO")
	private Integer anio;
	@Column(name = "SERVICIO")
	private String servicio;
	@Column(name = "CSI")
	private String csi;
	@Column(name = "COM")
	private Double com;
	@Column(name = "COM_SIN_IVA")
	private Double comSinIva;
	@Column(name = "IVAA")
	private Double ivaa;
	@Column(name = "TOTAL")
	private Double total;
	@Column(name = "TIPO_SOLICITUD")
	private Integer tipoSolicitud;
	@Column(name = "LLAVE")
	private String llave;
	@Column(name = "EJE")
	private String eje;
	@Column(name = "CATALOGADA")
	private String catalogada;
	@Column(name = "SECUENCIAL")
	private String secuencial;
	@Column(name = "FECHA")
	@Temporal(TemporalType.DATE)
	private Date fecha;
	@Column(name = "CONCEPTO")
	private String concepto;
	@Column(name = "LEYENDA")
	private String leyenda;
	@Column(name = "DIAS")
	private Integer dias;
	@Column(name = "ID_SERVICIO")
	private Integer idServicio;
	@Column(name = "ID_ONDEMAND")
	private Integer idOndemand;
	@Column(name = "EVALUACION_VIRTUAL")
	private Integer evaluacionVirtual;
	@Column(name = "OPEN_ITEM")
	private String openItem;
	@Column(name = "FRANQUICIA")
	private String franquicia;
	@Column(name = "SEC_ARC")
	private Integer secArc;
	@Column(name = "SEC_INT")
	private Integer secInt;
	@Column(name = "NO_CLIENTE")
	private Integer noCliente;
	@Column(name = "CTA_CLIENTE")
	private String ctaCliente;
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
	@Column(name = "NSECUENCIAL")
	private Integer nsecuencial;
	@Column(name = "FEC_VENC_PROTECCION")
	@Temporal(TemporalType.DATE)
	private Date fecVencProteccion;

	/**
	 * @return the id
	 */
	public Integer getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(Integer id) {
		this.id = id;
	}

	/**
	 * @return the cliente
	 */
	public Integer getCliente() {
		return cliente;
	}

	/**
	 * @param cliente the cliente to set
	 */
	public void setCliente(Integer cliente) {
		this.cliente = cliente;
	}

	/**
	 * @return the blanco
	 */
	public String getBlanco() {
		return blanco;
	}

	/**
	 * @param blanco the blanco to set
	 */
	public void setBlanco(String blanco) {
		this.blanco = blanco;
	}

	/**
	 * @return the importe
	 */
	public Double getImporte() {
		return importe;
	}

	/**
	 * @param importe the importe to set
	 */
	public void setImporte(Double importe) {
		this.importe = importe;
	}

	/**
	 * @return the iva
	 */
	public Integer getIva() {
		return iva;
	}

	/**
	 * @param iva the iva to set
	 */
	public void setIva(Integer iva) {
		this.iva = iva;
	}

	/**
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * @param status the status to set
	 */
	public void setStatus(String status) {
		this.status = status;
	}

	/**
	 * @return the mes
	 */
	public String getMes() {
		return mes;
	}

	/**
	 * @param mes the mes to set
	 */
	public void setMes(String mes) {
		this.mes = mes;
	}

	/**
	 * @return the anio
	 */
	public Integer getAnio() {
		return anio;
	}

	/**
	 * @param anio the anio to set
	 */
	public void setAnio(Integer anio) {
		this.anio = anio;
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

	/**
	 * @return the csi
	 */
	public String getCsi() {
		return csi;
	}

	/**
	 * @param csi the csi to set
	 */
	public void setCsi(String csi) {
		this.csi = csi;
	}

	/**
	 * @return the com
	 */
	public Double getCom() {
		return com;
	}

	/**
	 * @param com the com to set
	 */
	public void setCom(Double com) {
		this.com = com;
	}

	/**
	 * @return the comSinIva
	 */
	public Double getComSinIva() {
		return comSinIva;
	}

	/**
	 * @param comSinIva the comSinIva to set
	 */
	public void setComSinIva(Double comSinIva) {
		this.comSinIva = comSinIva;
	}

	/**
	 * @return the ivaa
	 */
	public Double getIvaa() {
		return ivaa;
	}

	/**
	 * @param ivaa the ivaa to set
	 */
	public void setIvaa(Double ivaa) {
		this.ivaa = ivaa;
	}

	/**
	 * @return the total
	 */
	public Double getTotal() {
		return total;
	}

	/**
	 * @param total the total to set
	 */
	public void setTotal(Double total) {
		this.total = total;
	}

	/**
	 * @return the tipoSolicitud
	 */
	public Integer getTipoSolicitud() {
		return tipoSolicitud;
	}

	/**
	 * @param tipoSolicitud the tipoSolicitud to set
	 */
	public void setTipoSolicitud(Integer tipoSolicitud) {
		this.tipoSolicitud = tipoSolicitud;
	}

	/**
	 * @return the llave
	 */
	public String getLlave() {
		return llave;
	}

	/**
	 * @param llave the llave to set
	 */
	public void setLlave(String llave) {
		this.llave = llave;
	}

	/**
	 * @return the eje
	 */
	public String getEje() {
		return eje;
	}

	/**
	 * @param eje the eje to set
	 */
	public void setEje(String eje) {
		this.eje = eje;
	}

	/**
	 * @return the catalogada
	 */
	public String getCatalogada() {
		return catalogada;
	}

	/**
	 * @param catalogada the catalogada to set
	 */
	public void setCatalogada(String catalogada) {
		this.catalogada = catalogada;
	}

	/**
	 * @return the secuencial
	 */
	public String getSecuencial() {
		return secuencial;
	}

	/**
	 * @param secuencial the secuencial to set
	 */
	public void setSecuencial(String secuencial) {
		this.secuencial = secuencial;
	}

	/**
	 * @return the fecha
	 */
	public Date getFecha() {
		return fecha;
	}

	/**
	 * @param fecha the fecha to set
	 */
	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	/**
	 * @return the concepto
	 */
	public String getConcepto() {
		return concepto;
	}

	/**
	 * @param concepto the concepto to set
	 */
	public void setConcepto(String concepto) {
		this.concepto = concepto;
	}

	/**
	 * @return the leyenda
	 */
	public String getLeyenda() {
		return leyenda;
	}

	/**
	 * @param leyenda the leyenda to set
	 */
	public void setLeyenda(String leyenda) {
		this.leyenda = leyenda;
	}

	/**
	 * @return the dias
	 */
	public Integer getDias() {
		return dias;
	}

	/**
	 * @param dias the dias to set
	 */
	public void setDias(Integer dias) {
		this.dias = dias;
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
	 * @return the evaluacionVirtual
	 */
	public Integer getEvaluacionVirtual() {
		return evaluacionVirtual;
	}

	/**
	 * @param evaluacionVirtual the evaluacionVirtual to set
	 */
	public void setEvaluacionVirtual(Integer evaluacionVirtual) {
		this.evaluacionVirtual = evaluacionVirtual;
	}

	/**
	 * @return the openItem
	 */
	public String getOpenItem() {
		return openItem;
	}

	/**
	 * @param openItem the openItem to set
	 */
	public void setOpenItem(String openItem) {
		this.openItem = openItem;
	}

	/**
	 * @return the franquicia
	 */
	public String getFranquicia() {
		return franquicia;
	}

	/**
	 * @param franquicia the franquicia to set
	 */
	public void setFranquicia(String franquicia) {
		this.franquicia = franquicia;
	}

	/**
	 * @return the secArc
	 */
	public Integer getSecArc() {
		return secArc;
	}

	/**
	 * @param secArc the secArc to set
	 */
	public void setSecArc(Integer secArc) {
		this.secArc = secArc;
	}

	/**
	 * @return the secInt
	 */
	public Integer getSecInt() {
		return secInt;
	}

	/**
	 * @param secInt the secInt to set
	 */
	public void setSecInt(Integer secInt) {
		this.secInt = secInt;
	}

	/**
	 * @return the noCliente
	 */
	public Integer getNoCliente() {
		return noCliente;
	}

	/**
	 * @param noCliente the noCliente to set
	 */
	public void setNoCliente(Integer noCliente) {
		this.noCliente = noCliente;
	}

	/**
	 * @return the ctaCliente
	 */
	public String getCtaCliente() {
		return ctaCliente;
	}

	/**
	 * @param ctaCliente the ctaCliente to set
	 */
	public void setCtaCliente(String ctaCliente) {
		this.ctaCliente = ctaCliente;
	}

	/**
	 * @return the contrato
	 */
	public String getContrato() {
		return contrato;
	}

	/**
	 * @param contrato the contrato to set
	 */
	public void setContrato(String contrato) {
		this.contrato = contrato;
	}

	/**
	 * @return the impOperacion1
	 */
	public Double getImpOperacion1() {
		return impOperacion1;
	}

	/**
	 * @param impOperacion1 the impOperacion1 to set
	 */
	public void setImpOperacion1(Double impOperacion1) {
		this.impOperacion1 = impOperacion1;
	}

	/**
	 * @return the impOperacion2
	 */
	public Double getImpOperacion2() {
		return impOperacion2;
	}

	/**
	 * @param impOperacion2 the impOperacion2 to set
	 */
	public void setImpOperacion2(Double impOperacion2) {
		this.impOperacion2 = impOperacion2;
	}

	/**
	 * @return the codOperacion
	 */
	public String getCodOperacion() {
		return codOperacion;
	}

	/**
	 * @param codOperacion the codOperacion to set
	 */
	public void setCodOperacion(String codOperacion) {
		this.codOperacion = codOperacion;
	}

	/**
	 * @return the descRechazo
	 */
	public String getDescRechazo() {
		return descRechazo;
	}

	/**
	 * @param descRechazo the descRechazo to set
	 */
	public void setDescRechazo(String descRechazo) {
		this.descRechazo = descRechazo;
	}

	/**
	 * @return the leyendaEmisor
	 */
	public String getLeyendaEmisor() {
		return leyendaEmisor;
	}

	/**
	 * @param leyendaEmisor the leyendaEmisor to set
	 */
	public void setLeyendaEmisor(String leyendaEmisor) {
		this.leyendaEmisor = leyendaEmisor;
	}

	/**
	 * @return the fecOperacion
	 */
	public Date getFecOperacion() {
		return fecOperacion;
	}

	/**
	 * @param fecOperacion the fecOperacion to set
	 */
	public void setFecOperacion(Date fecOperacion) {
		this.fecOperacion = fecOperacion;
	}

	/**
	 * @return the numProteccion
	 */
	public String getNumProteccion() {
		return numProteccion;
	}

	/**
	 * @param numProteccion the numProteccion to set
	 */
	public void setNumProteccion(String numProteccion) {
		this.numProteccion = numProteccion;
	}

	/**
	 * @return the nsecuencial
	 */
	public Integer getNsecuencial() {
		return nsecuencial;
	}

	/**
	 * @param nsecuencial the nsecuencial to set
	 */
	public void setNsecuencial(Integer nsecuencial) {
		this.nsecuencial = nsecuencial;
	}

	/**
	 * @return the fecVencProteccion
	 */
	public Date getFecVencProteccion() {
		return fecVencProteccion;
	}

	/**
	 * @param fecVencProteccion the fecVencProteccion to set
	 */
	public void setFecVencProteccion(Date fecVencProteccion) {
		this.fecVencProteccion = fecVencProteccion;
	}
	
		

}

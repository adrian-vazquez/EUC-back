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
@Table(name = "PPC_MIS_PRONOSTICOS_ALT_CHEQ")
public class CatalogoPronosticosAltCheq implements Serializable {

	
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
	@Column(name = "CUENTA")
	private Integer cuenta;
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
	private String Servicio;
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
	@Column(name = "TIPO_COMISION")
	private Integer tipoComision;
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
	@Column(name = "CONCPETO")
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
	
	
	public CatalogoPronosticosAltCheq() {
		// TODO Auto-generated constructor stub
	}


	public CatalogoPronosticosAltCheq(Integer id, Integer cliente, String blanco, Integer cuenta, Double importe,
			Integer iva, String status, String mes, Integer anio, String servicio, String csi, Double com,
			Double comSinIva, Double ivaa, Double total, Integer tipoComision, String llave, String eje,
			String catalogada, String secuencial, Date fecha, String concepto, String leyenda, Integer dias,
			Integer idServicio, Integer idOndemand, Integer evaluacionVirtual) {
		super();
		this.id = id;
		this.cliente = cliente;
		this.blanco = blanco;
		this.cuenta = cuenta;
		this.importe = importe;
		this.iva = iva;
		this.status = status;
		this.mes = mes;
		this.anio = anio;
		Servicio = servicio;
		this.csi = csi;
		this.com = com;
		this.comSinIva = comSinIva;
		this.ivaa = ivaa;
		this.total = total;
		this.tipoComision = tipoComision;
		this.llave = llave;
		this.eje = eje;
		this.catalogada = catalogada;
		this.secuencial = secuencial;
		this.fecha = fecha;
		this.concepto = concepto;
		this.leyenda = leyenda;
		this.dias = dias;
		this.idServicio = idServicio;
		this.idOndemand = idOndemand;
		this.evaluacionVirtual = evaluacionVirtual;
	}


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
	 * @return the cuenta
	 */
	public Integer getCuenta() {
		return cuenta;
	}


	/**
	 * @param cuenta the cuenta to set
	 */
	public void setCuenta(Integer cuenta) {
		this.cuenta = cuenta;
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
		return Servicio;
	}


	/**
	 * @param servicio the servicio to set
	 */
	public void setServicio(String servicio) {
		Servicio = servicio;
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
	 * @return the com_sin_iva
	 */
	public Double getComSinIva() {
		return comSinIva;
	}


	/**
	 * @param com_sin_iva the com_sin_iva to set
	 */
	public void getComSinIva(Double comSinIva) {
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
	 * @return the tipo_comision
	 */
	public Integer getTipoComision() {
		return tipoComision;
	}


	/**
	 * @param tipo_comision the tipo_comision to set
	 */
	public void setTipoComision(Integer tipoComision) {
		this.tipoComision = tipoComision;
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
	 * @return the id_servicio
	 */
	public Integer getIdServicio() {
		return idServicio;
	}


	/**
	 * @param id_servicio the id_servicio to set
	 */
	public void setIdServicio(Integer idServicio) {
		this.idServicio = idServicio;
	}


	/**
	 * @return the id_ondemand
	 */
	public Integer getIdOndemand() {
		return idOndemand;
	}


	/**
	 * @param id_ondemand the id_ondemand to set
	 */
	public void setIdOndemand(Integer idOndemand) {
		this.idOndemand = idOndemand;
	}


	/**
	 * @return the evaluacion_virtual
	 */
	public Integer getEvaluacionVirtual() {
		return evaluacionVirtual;
	}


	/**
	 * @param evaluacion_virtual the evaluacion_virtual to set
	 */
	public void setEvaluacionVirtual(Integer evaluacionVirtual) {
		this.evaluacionVirtual = evaluacionVirtual;
	}
	
	

}

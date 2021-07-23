package com.citi.euces.pronosticos.infra.dto;

import java.util.Date;

public class PronosticosFinalDTO {

	private Integer id;
	private Integer cliente;
	private String blanco;
	private Long cuenta;
	private Double importe;
	private Integer iva;
	private String status; 
	private String mes;
	private Integer anio;
	private String servicio;
	private String csi;
	private String com;
	private Double comSinIva;
	private Double ivaa;
	private Double total;
	private Integer tipoComision;
	private String llave;
	private String eje;
	private String catalogada;
	private String secuencial;
	private Date fecha;
	private String concepto;
	private String leyenda;
	private Integer dias;
	private Long idServicio;
	private Long idOndemand;
	private Integer evaluacionVirtual;
	private String cuentacliente;
	private String contrato;
	private String franquicia;
	private String numProtec;
	private Date fecVencProtec;
	private String openItem;
	
	
	
	public PronosticosFinalDTO(Integer id, Integer cliente, String blanco, Long cuenta, Double importe, Integer iva,
			String status, String mes, Integer anio, String servicio, String csi, String com, Double comSinIva,
			Double ivaa, Double total, Integer tipoComision, String llave, String eje, String catalogada,
			String secuencial, Date fecha, String concepto, String leyenda, Integer dias, Long idServicio,
			Long idOndemand, Integer evaluacionVirtual, String cuentacliente, String contrato, String franquicia,
			String numProtec, Date fecVencProtec, String openItem) {
		this.id = id;
		this.cliente = cliente;
		this.blanco = blanco;
		this.cuenta = cuenta;
		this.importe = importe;
		this.iva = iva;
		this.status = status;
		this.mes = mes;
		this.anio = anio;
		this.servicio = servicio;
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
		this.cuentacliente = cuentacliente;
		this.contrato = contrato;
		this.franquicia = franquicia;
		this.numProtec = numProtec;
		this.fecVencProtec = fecVencProtec;
		this.openItem = openItem;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getCliente() {
		return cliente;
	}
	public void setCliente(Integer cliente) {
		this.cliente = cliente;
	}
	public String getBlanco() {
		return blanco;
	}
	public void setBlanco(String blanco) {
		this.blanco = blanco;
	}
	public Long getCuenta() {
		return cuenta;
	}
	public void setCuenta(Long cuenta) {
		this.cuenta = cuenta;
	}
	public Double getImporte() {
		return importe;
	}
	public void setImporte(Double importe) {
		this.importe = importe;
	}
	public Integer getIva() {
		return iva;
	}
	public void setIva(Integer iva) {
		this.iva = iva;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getMes() {
		return mes;
	}
	public void setMes(String mes) {
		this.mes = mes;
	}
	public Integer getAnio() {
		return anio;
	}
	public void setAnio(Integer anio) {
		this.anio = anio;
	}
	public String getServicio() {
		return servicio;
	}
	public void setServicio(String servicio) {
		this.servicio = servicio;
	}
	public String getCsi() {
		return csi;
	}
	public void setCsi(String csi) {
		this.csi = csi;
	}
	public String getCom() {
		return com;
	}
	public void setCom(String com) {
		this.com = com;
	}
	public Double getComSinIva() {
		return comSinIva;
	}
	public void setComSinIva(Double comSinIva) {
		this.comSinIva = comSinIva;
	}
	public Double getIvaa() {
		return ivaa;
	}
	public void setIvaa(Double ivaa) {
		this.ivaa = ivaa;
	}
	public Double getTotal() {
		return total;
	}
	public void setTotal(Double total) {
		this.total = total;
	}
	public Integer getTipoComision() {
		return tipoComision;
	}
	public void setTipoComision(Integer tipoComision) {
		this.tipoComision = tipoComision;
	}
	public String getLlave() {
		return llave;
	}
	public void setLlave(String llave) {
		this.llave = llave;
	}
	public String getEje() {
		return eje;
	}
	public void setEje(String eje) {
		this.eje = eje;
	}
	public String getCatalogada() {
		return catalogada;
	}
	public void setCatalogada(String catalogada) {
		this.catalogada = catalogada;
	}
	public String getSecuencial() {
		return secuencial;
	}
	public void setSecuencial(String secuencial) {
		this.secuencial = secuencial;
	}
	public Date getFecha() {
		return fecha;
	}
	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}
	public String getConcepto() {
		return concepto;
	}
	public void setConcepto(String concepto) {
		this.concepto = concepto;
	}
	public String getLeyenda() {
		return leyenda;
	}
	public void setLeyenda(String leyenda) {
		this.leyenda = leyenda;
	}
	public Integer getDias() {
		return dias;
	}
	public void setDias(Integer dias) {
		this.dias = dias;
	}
	public Long getIdServicio() {
		return idServicio;
	}
	public void setIdServicio(Long idServicio) {
		this.idServicio = idServicio;
	}
	public Long getIdOndemand() {
		return idOndemand;
	}
	public void setIdOndemand(Long idOndemand) {
		this.idOndemand = idOndemand;
	}
	public Integer getEvaluacionVirtual() {
		return evaluacionVirtual;
	}
	public void setEvaluacionVirtual(Integer evaluacionVirtual) {
		this.evaluacionVirtual = evaluacionVirtual;
	}
	public String getCuentacliente() {
		return cuentacliente;
	}
	public void setCuentacliente(String cuentacliente) {
		this.cuentacliente = cuentacliente;
	}
	public String getContrato() {
		return contrato;
	}
	public void setContrato(String contrato) {
		this.contrato = contrato;
	}
	public String getFranquicia() {
		return franquicia;
	}
	public void setFranquicia(String franquicia) {
		this.franquicia = franquicia;
	}
	public String getNumProtec() {
		return numProtec;
	}
	public void setNumProtec(String numProtec) {
		this.numProtec = numProtec;
	}
	public Date getFecVencProtec() {
		return fecVencProtec;
	}
	public void setFecVencProtec(Date fecVencProtec) {
		this.fecVencProtec = fecVencProtec;
	}
	public String getOpenItem() {
		return openItem;
	}
	public void setOpenItem(String openItem) {
		this.openItem = openItem;
	}
	
}

package com.citi.euces.pronosticos.infra.dto;

import java.io.Serializable;

public class ImpReporteCobroDTO implements Serializable{

	private static final long serialVersionUID = 1L;

	private Integer numCliente;
	private String ctaEje;
	private String numCtaResp;
	private Double total;
	private String mes;
	private Integer anio;
	private String servicio;
	private Double comisionSinIva;
	private Double ivaa;
	private String llave;
	private String noProteccion;
	private String fechaCobro;
	private String fechaContable;
	private String cntrClienteUsuario;
	
	public ImpReporteCobroDTO(Integer numCliente, String ctaEje, String numCtaResp, Double total, String mes,
			Integer anio, String servicio, Double comisionSinIva, Double ivaa, String llave, String noProteccion,
			String fechaCobro, String fechaContable, String cntrClienteUsuario) {
		super();
		this.numCliente = numCliente;
		this.ctaEje = ctaEje;
		this.numCtaResp = numCtaResp;
		this.total = total;
		this.mes = mes;
		this.anio = anio;
		this.servicio = servicio;
		this.comisionSinIva = comisionSinIva;
		this.ivaa = ivaa;
		this.llave = llave;
		this.noProteccion = noProteccion;
		this.fechaCobro = fechaCobro;
		this.fechaContable = fechaContable;
		this.cntrClienteUsuario = cntrClienteUsuario;
	}

	public Integer getNumCliente() {
		return numCliente;
	}

	public void setNumCliente(Integer numCliente) {
		this.numCliente = numCliente;
	}

	public String getCtaEje() {
		return ctaEje;
	}

	public void setCtaEje(String ctaEje) {
		this.ctaEje = ctaEje;
	}

	public String getNumCtaResp() {
		return numCtaResp;
	}

	public void setNumCtaResp(String numCtaResp) {
		this.numCtaResp = numCtaResp;
	}

	public Double getTotal() {
		return total;
	}

	public void setTotal(Double total) {
		this.total = total;
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

	public Double getComisionSinIva() {
		return comisionSinIva;
	}

	public void setComisionSinIva(Double comisionSinIva) {
		this.comisionSinIva = comisionSinIva;
	}

	public Double getIvaa() {
		return ivaa;
	}

	public void setIvaa(Double ivaa) {
		this.ivaa = ivaa;
	}

	public String getLlave() {
		return llave;
	}

	public void setLlave(String llave) {
		this.llave = llave;
	}

	public String getNoProteccion() {
		return noProteccion;
	}

	public void setNoProteccion(String noProteccion) {
		this.noProteccion = noProteccion;
	}

	public String getFechaCobro() {
		return fechaCobro;
	}

	public void setFechaCobro(String fechaCobro) {
		this.fechaCobro = fechaCobro;
	}

	public String getFechaContable() {
		return fechaContable;
	}

	public void setFechaContable(String fechaContable) {
		this.fechaContable = fechaContable;
	}

	public String getCntrClienteUsuario() {
		return cntrClienteUsuario;
	}

	public void setCntrClienteUsuario(String cntrClienteUsuario) {
		this.cntrClienteUsuario = cntrClienteUsuario;
	}
	
}
package com.citi.euces.pronosticos.infra.dto;

public class TbBloqueosBEDTO {

	public Long no_cliente;
    public String chequera;
    public Long sucursal;
    public Long cuenta;
    public String mes;
    public String anio;
    public String descripcion;
    public String servicio;
    
	public TbBloqueosBEDTO() {
		super();
	}
	
	public TbBloqueosBEDTO(Long no_cliente, Long sucursal, Long cuenta, String mes, String anio, String descripcion,
			String servicio) {
		super();
		this.no_cliente = no_cliente;
		this.sucursal = sucursal;
		this.cuenta = cuenta;
		this.mes = mes;
		this.anio = anio;
		this.descripcion = descripcion;
		this.servicio = servicio;
	}

	public Long getNo_cliente() {
		return no_cliente;
	}
	public void setNo_cliente(Long no_cliente) {
		this.no_cliente = no_cliente;
	}
	public String getChequera() {
		return chequera;
	}
	public void setChequera(String chequera) {
		this.chequera = chequera;
	}
	public Long getSucursal() {
		return sucursal;
	}
	public void setSucursal(Long sucursal) {
		this.sucursal = sucursal;
	}
	public Long getCuenta() {
		return cuenta;
	}
	public void setCuenta(Long cuenta) {
		this.cuenta = cuenta;
	}
	public String getMes() {
		return mes;
	}
	public void setMes(String mes) {
		this.mes = mes;
	}
	public String getAnio() {
		return anio;
	}
	public void setAnio(String anio) {
		this.anio = anio;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	public String getServicio() {
		return servicio;
	}
	public void setServicio(String servicio) {
		this.servicio = servicio;
	}
	
    
}

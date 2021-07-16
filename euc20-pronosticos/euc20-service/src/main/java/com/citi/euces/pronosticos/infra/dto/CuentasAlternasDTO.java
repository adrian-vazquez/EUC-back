package com.citi.euces.pronosticos.infra.dto;

import java.io.Serializable;

public class CuentasAlternasDTO implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	public Long num_Cliente;
    public Long num_Producto;
    public Long cve_Instrumento;
    public Long num_Contrato;
    public Long cve_Estatus;
    public Double sdo_Actual;
    public Long prefmda;
    public Long cuentamda;
    public String cuantaAlterna;
    
	public CuentasAlternasDTO() {
	}
	
	public CuentasAlternasDTO(Long num_Cliente, Long num_Producto, Long cve_Instrumento, Long num_Contrato,
			Long cve_Estatus, Double sdo_Actual, Long prefmda, Long cuentamda) {
		super();
		this.num_Cliente = num_Cliente;
		this.num_Producto = num_Producto;
		this.cve_Instrumento = cve_Instrumento;
		this.num_Contrato = num_Contrato;
		this.cve_Estatus = cve_Estatus;
		this.sdo_Actual = sdo_Actual;
		this.prefmda = prefmda;
		this.cuentamda = cuentamda;
	}
	/**
	 * @return the num_Cliente
	 */
	public Long getNum_Cliente() {
		return num_Cliente;
	}
	/**
	 * @param num_Cliente the num_Cliente to set
	 */
	public void setNum_Cliente(Long num_Cliente) {
		this.num_Cliente = num_Cliente;
	}
	/**
	 * @return the num_Producto
	 */
	public Long getNum_Producto() {
		return num_Producto;
	}
	/**
	 * @param num_Producto the num_Producto to set
	 */
	public void setNum_Producto(Long num_Producto) {
		this.num_Producto = num_Producto;
	}
	/**
	 * @return the cve_Instrumento
	 */
	public Long getCve_Instrumento() {
		return cve_Instrumento;
	}
	/**
	 * @param cve_Instrumento the cve_Instrumento to set
	 */
	public void setCve_Instrumento(Long cve_Instrumento) {
		this.cve_Instrumento = cve_Instrumento;
	}
	/**
	 * @return the num_Contrato
	 */
	public Long getNum_Contrato() {
		return num_Contrato;
	}
	/**
	 * @param num_Contrato the num_Contrato to set
	 */
	public void setNum_Contrato(Long num_Contrato) {
		this.num_Contrato = num_Contrato;
	}
	/**
	 * @return the cve_Estatus
	 */
	public Long getCve_Estatus() {
		return cve_Estatus;
	}
	/**
	 * @param cve_Estatus the cve_Estatus to set
	 */
	public void setCve_Estatus(Long cve_Estatus) {
		this.cve_Estatus = cve_Estatus;
	}
	/**
	 * @return the sdo_Actual
	 */
	public Double getSdo_Actual() {
		return sdo_Actual;
	}
	/**
	 * @param sdo_Actual the sdo_Actual to set
	 */
	public void setSdo_Actual(Double sdo_Actual) {
		this.sdo_Actual = sdo_Actual;
	}
	/**
	 * @return the prefmda
	 */
	public Long getPrefmda() {
		return prefmda;
	}
	/**
	 * @param prefmda the prefmda to set
	 */
	public void setPrefmda(Long prefmda) {
		this.prefmda = prefmda;
	}
	/**
	 * @return the cuentamda
	 */
	public Long getCuentamda() {
		return cuentamda;
	}
	/**
	 * @param cuentamda the cuentamda to set
	 */
	public void setCuentamda(Long cuentamda) {
		this.cuentamda = cuentamda;
	}
	/**
	 * @return the cuantaAlterna
	 */
	public String getCuantaAlterna() {
		return cuantaAlterna;
	}
	/**
	 * @param cuantaAlterna the cuantaAlterna to set
	 */
	public void setCuantaAlterna(String cuantaAlterna) {
		this.cuantaAlterna = cuantaAlterna;
	}
    
    

}

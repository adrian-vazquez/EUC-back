package com.citi.euces.pronosticos.infra.dto;

import java.io.Serializable;

public class SlunifinalexcPerfDTO implements Serializable{

	private Long numCliente;
	private Long numContrato;
	private Long sucursal;
	private Double sdoFinMes;
	private Double sdoFromMes;
	private Long suc;
	private Long cuenta;
	private Double Diferencia;
	private Double com;
	private String llavePre;
	private Double sdoFinAct;
	private Double sdoFromAct;
	private Long frontera;
	
	public Long getNumCliente() {
		return numCliente;
	}
	public void setNumCliente(Long numCliente) {
		this.numCliente = numCliente;
	}
	public Long getNumContrato() {
		return numContrato;
	}
	public void setNumContrato(Long numContrato) {
		this.numContrato = numContrato;
	}
	public Long getSucursal() {
		return sucursal;
	}
	public void setSucursal(Long sucursal) {
		this.sucursal = sucursal;
	}
	public Double getSdoFinMes() {
		return sdoFinMes;
	}
	public void setSdoFinMes(Double sdoFinMes) {
		this.sdoFinMes = sdoFinMes;
	}
	public Double getSdoFromMes() {
		return sdoFromMes;
	}
	public void setSdoFromMes(Double sdoFromMes) {
		this.sdoFromMes = sdoFromMes;
	}
	public Long getSuc() {
		return suc;
	}
	public void setSuc(Long suc) {
		this.suc = suc;
	}
	public Long getCuenta() {
		return cuenta;
	}
	public void setCuenta(Long cuenta) {
		this.cuenta = cuenta;
	}
	public Double getDiferencia() {
		return Diferencia;
	}
	public void setDiferencia(Double diferencia) {
		Diferencia = diferencia;
	}
	public Double getCom() {
		return com;
	}
	public void setCom(Double com) {
		this.com = com;
	}
	public String getLlavePre() {
		return llavePre;
	}
	public void setLlavePre(String llavePre) {
		this.llavePre = llavePre;
	}
	public Double getSdoFinAct() {
		return sdoFinAct;
	}
	public void setSdoFinAct(Double sdoFinAct) {
		this.sdoFinAct = sdoFinAct;
	}
	public Double getSdoFromAct() {
		return sdoFromAct;
	}
	public void setSdoFromAct(Double sdoFromAct) {
		this.sdoFromAct = sdoFromAct;
	}
	public Long getFrontera() {
		return frontera;
	}
	public void setFrontera(Long frontera) {
		this.frontera = frontera;
	}
}

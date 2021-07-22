package com.citi.euces.pronosticos.infra.dto;

import java.io.Serializable;

public class CuentasVirtulesGposDTO implements Serializable{

	private Long numCliente;
	private Long numCuenta;
	private String nombre;
	private Long ctasV;
	private Double TxnsBe;
	private Double TxnsVent;
	private Double tarifaBe;
	private Double tarifaVent;
	private Double tarifaMens;
	private Double comBe;
	private Double comVent;
	private Double comMens;
	private String uso11;
	private Double comTotal;
	private int suc;
	private Long cuenta;
	private Long franquicia;
	private int moneda;
	private Double iva;
	
	public CuentasVirtulesGposDTO(Long numCliente, Long numCuenta, String nombre, Long ctasV, Double txnsBe,
			Double txnsVent, Double tarifaBe, Double tarifaVent, Double tarifaMens, Double comBe, Double comVent,
			Double comMnes, String uso11, Double comTotal, int suc, Long cuenta, Long franquicia, int moneda,
			Double iva) {
		super();
		this.numCliente = numCliente;
		this.numCuenta = numCuenta;
		this.nombre = nombre;
		this.ctasV = ctasV;
		this.TxnsBe = txnsBe;
		this.TxnsVent = txnsVent;
		this.tarifaBe = tarifaBe;
		this.tarifaVent = tarifaVent;
		this.tarifaMens = tarifaMens;
		this.comBe = comBe;
		this.comVent = comVent;
		this.comMens = comMnes;
		this.uso11 = uso11;
		this.comTotal = comTotal;
		this.suc = suc;
		this.cuenta = cuenta;
		this.franquicia = franquicia;
		this.moneda = moneda;
		this.iva = iva;
	}
	
	public Long getNumCliente() {
		return numCliente;
	}
	public void setNumCliente(Long numCliente) {
		this.numCliente = numCliente;
	}
	public Long getNumCuenta() {
		return numCuenta;
	}
	public void setNumCuenta(Long numCuenta) {
		this.numCuenta = numCuenta;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public Long getCtasV() {
		return ctasV;
	}
	public void setCtasV(Long ctasV) {
		this.ctasV = ctasV;
	}
	public Double getTxnsBe() {
		return TxnsBe;
	}
	public void setTxnsBe(Double txnsBe) {
		TxnsBe = txnsBe;
	}
	public Double getTxnsVent() {
		return TxnsVent;
	}
	public void setTxnsVent(Double txnsVent) {
		TxnsVent = txnsVent;
	}
	public Double getTarifaBe() {
		return tarifaBe;
	}
	public void setTarifaBe(Double tarifaBe) {
		this.tarifaBe = tarifaBe;
	}
	public Double getTarifaVent() {
		return tarifaVent;
	}
	public void setTarifaVent(Double tarifaVent) {
		this.tarifaVent = tarifaVent;
	}
	public Double getTarifaMens() {
		return tarifaMens;
	}
	public void setTarifaMens(Double tarifaMens) {
		this.tarifaMens = tarifaMens;
	}
	public Double getComBe() {
		return comBe;
	}
	public void setComBe(Double comBe) {
		this.comBe = comBe;
	}
	public Double getComVent() {
		return comVent;
	}
	public void setComVent(Double comVent) {
		this.comVent = comVent;
	}
	public Double getComMens() {
		return comMens;
	}
	public void setComMens(Double comMens) {
		this.comMens = comMens;
	}
	public String getUso11() {
		return uso11;
	}
	public void setUso11(String uso11) {
		this.uso11 = uso11;
	}
	public Double getComTotal() {
		return comTotal;
	}
	public void setComTotal(Double comTotal) {
		this.comTotal = comTotal;
	}
	public int getSuc() {
		return suc;
	}
	public void setSuc(int suc) {
		this.suc = suc;
	}
	public Long getCuenta() {
		return cuenta;
	}
	public void setCuenta(Long cuenta) {
		this.cuenta = cuenta;
	}
	public Long getFranquicia() {
		return franquicia;
	}
	public void setFranquicia(Long franquicia) {
		this.franquicia = franquicia;
	}
	public int getMoneda() {
		return moneda;
	}
	public void setMoneda(int moneda) {
		this.moneda = moneda;
	}
	public Double getIva() {
		return iva;
	}
	public void setIva(Double iva) {
		this.iva = iva;
	}
}

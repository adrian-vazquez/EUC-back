package com.citi.euces.pronosticos.entities;

import java.io.Serializable;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name = "PPC_PCB_CTAS_VIRTUALES_GPOS")
public class CtasVirtualesGpos implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Column(name = "NUM_CLIENTE")
	private Long numCliente;
	
	@Column(name = "NUM_CUENTA")
	private Long numCuenta;
	
	@Size(min = 1, max = 55)
	@Column(name = "NOMBRE")
	private String nombre;
	
	@Column(name = "CTAS_V")
	private Long ctasV;
	
	@Column(name = "TXNS_BE")
	private Double txnsBe;
	
	@Column(name = "TXNS_VENT")
	private Double txnsVent;
	
	@Column(name = "TARIFA_BE")
	private Double tarifaBe;
	
	@Column(name = "TARIFA_VENT")
	private Double tarifaVent;
	
	@Column(name = "TARIFA_MENS")
	private Double tarifaMens;
	
	@Column(name = "COM_BE")
	private Double comBe;
	
	@Column(name = "COM_VENT")
	private Double comVent;
	
	@Column(name = "COM_MENS")
	private Double comMens;
	
	@Size(min = 1, max = 20)
	@Column(name = "USO_11")
	private String uso11;
	
	@Column(name = "COM_TOTAL")
	private Double comTotal;
	
	@Column(name = "SUC")
	private Integer suc;
	
	@Column(name = "CUENTA")
	private Long cuenta;
	
	@Column(name = "FRANQUICIA")
	private Integer franquicia;
	
	@Column(name = "MONEDA")
	private Double moneda;
	
	@Column(name = "IVA")
	private Double iva;
	
	@Id
	@Basic(optional = false)
	@NotNull
	@Column(name = "ID")
	private Long id;

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
		return txnsBe;
	}

	public void setTxnsBe(Double txnsBe) {
		this.txnsBe = txnsBe;
	}

	public Double getTxnsVent() {
		return txnsVent;
	}

	public void setTxnsVent(Double txnsVent) {
		this.txnsVent = txnsVent;
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

	public Integer getSuc() {
		return suc;
	}

	public void setSuc(Integer suc) {
		this.suc = suc;
	}

	public Long getCuenta() {
		return cuenta;
	}

	public void setCuenta(Long cuenta) {
		this.cuenta = cuenta;
	}

	public Integer getFranquicia() {
		return franquicia;
	}

	public void setFranquicia(Integer franquicia) {
		this.franquicia = franquicia;
	}

	public Double getMoneda() {
		return moneda;
	}

	public void setMoneda(Double moneda) {
		this.moneda = moneda;
	}

	public Double getIva() {
		return iva;
	}

	public void setIva(Double iva) {
		this.iva = iva;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	
}

package com.citi.euces.pronosticos.infra.dto;

import java.io.Serializable;

public class ReintentosMaestroComiDTO implements Serializable {

	private static final long serialVersionUID = 1L;
	private Long noCliente;
	private String chequera;
	private String sucursal;
	private String cuenta;
	private Integer idServicio;
	private Integer idOndemand;
	private Integer mes;
	private Integer anio;
	private Integer pIva;
	private Double mComision;
	private Double mIva;
	private Double mTotal;
	private Double mComParcial;
	private Double mIvaParcial;
	private Integer idEstatusComision;
	private Integer idCausaRechazo;
	private String fMovimiento;
	private String fRegistroContable;
	private String llave;
	private Integer catalogadaGc;
	private String csi;
	private String comEc;
	private String comP;
	private String chequeraCargo;
	private Integer noProteccion;
	private String llaveTemporal;
	private String mc;
	private Integer estatusProteccion;
	private String contrato;
	private String fIngreso;
	private Integer udemer;
	private String fraqnuiciaRegistro;
	private Integer idFranquicia;

	public ReintentosMaestroComiDTO(Long noCliente, String chequera, String sucursal, String cuenta, Integer idServicio,
			Integer idOndemand, Integer mes, Integer anio, Integer pIva, Double mComision, Double mIva, Double mTotal,
			Double mComParcial, Double mIvaParcial, Integer idEstatusComision, Integer idCausaRechazo,
			String fMovimiento, String fRegistroContable, String llave, Integer catalogadaGc, String csi, String comEc,
			String comP, String chequeraCargo, Integer noProteccion, String llaveTemporal, String mc,
			Integer estatusProteccion, String contrato, String fIngreso, Integer udemer, String fraqnuiciaRegistro,
			Integer idFranquicia) {
		super();
		this.noCliente = noCliente;
		this.chequera = chequera;
		this.sucursal = sucursal;
		this.cuenta = cuenta;
		this.idServicio = idServicio;
		this.idOndemand = idOndemand;
		this.mes = mes;
		this.anio = anio;
		this.pIva = pIva;
		this.mComision = mComision;
		this.mIva = mIva;
		this.mTotal = mTotal;
		this.mComParcial = mComParcial;
		this.mIvaParcial = mIvaParcial;
		this.idEstatusComision = idEstatusComision;
		this.idCausaRechazo = idCausaRechazo;
		this.fMovimiento = fMovimiento;
		this.fRegistroContable = fRegistroContable;
		this.llave = llave;
		this.catalogadaGc = catalogadaGc;
		this.csi = csi;
		this.comEc = comEc;
		this.comP = comP;
		this.chequeraCargo = chequeraCargo;
		this.noProteccion = noProteccion;
		this.llaveTemporal = llaveTemporal;
		this.mc = mc;
		this.estatusProteccion = estatusProteccion;
		this.contrato = contrato;
		this.fIngreso = fIngreso;
		this.udemer = udemer;
		this.fraqnuiciaRegistro = fraqnuiciaRegistro;
		this.idFranquicia = idFranquicia;
	}

	public Long getNoCliente() {
		return noCliente;
	}

	public void setNoCliente(Long noCliente) {
		this.noCliente = noCliente;
	}

	public String getChequera() {
		return chequera;
	}

	public void setChequera(String chequera) {
		this.chequera = chequera;
	}

	public String getSucursal() {
		return sucursal;
	}

	public void setSucursal(String sucursal) {
		this.sucursal = sucursal;
	}

	public String getCuenta() {
		return cuenta;
	}

	public void setCuenta(String cuenta) {
		this.cuenta = cuenta;
	}

	public Integer getIdServicio() {
		return idServicio;
	}

	public void setIdServicio(Integer idServicio) {
		this.idServicio = idServicio;
	}

	public Integer getIdOndemand() {
		return idOndemand;
	}

	public void setIdOndemand(Integer idOndemand) {
		this.idOndemand = idOndemand;
	}

	public Integer getMes() {
		return mes;
	}

	public void setMes(Integer mes) {
		this.mes = mes;
	}

	public Integer getAnio() {
		return anio;
	}

	public void setAnio(Integer anio) {
		this.anio = anio;
	}

	public Integer getpIva() {
		return pIva;
	}

	public void setpIva(Integer pIva) {
		this.pIva = pIva;
	}

	public Double getmComision() {
		return mComision;
	}

	public void setmComision(Double mComision) {
		this.mComision = mComision;
	}

	public Double getmIva() {
		return mIva;
	}

	public void setmIva(Double mIva) {
		this.mIva = mIva;
	}

	public Double getmTotal() {
		return mTotal;
	}

	public void setmTotal(Double mTotal) {
		this.mTotal = mTotal;
	}

	public Double getmComParcial() {
		return mComParcial;
	}

	public void setmComParcial(Double mComParcial) {
		this.mComParcial = mComParcial;
	}

	public Double getmIvaParcial() {
		return mIvaParcial;
	}

	public void setmIvaParcial(Double mIvaParcial) {
		this.mIvaParcial = mIvaParcial;
	}

	public Integer getIdEstatusComision() {
		return idEstatusComision;
	}

	public void setIdEstatusComision(Integer idEstatusComision) {
		this.idEstatusComision = idEstatusComision;
	}

	public Integer getIdCausaRechazo() {
		return idCausaRechazo;
	}

	public void setIdCausaRechazo(Integer idCausaRechazo) {
		this.idCausaRechazo = idCausaRechazo;
	}

	public String getfMovimiento() {
		return fMovimiento;
	}

	public void setfMovimiento(String fMovimiento) {
		this.fMovimiento = fMovimiento;
	}

	public String getfRegistroContable() {
		return fRegistroContable;
	}

	public void setfRegistroContable(String fRegistroContable) {
		this.fRegistroContable = fRegistroContable;
	}

	public String getLlave() {
		return llave;
	}

	public void setLlave(String llave) {
		this.llave = llave;
	}

	public Integer getCatalogadaGc() {
		return catalogadaGc;
	}

	public void setCatalogadaGc(Integer catalogadaGc) {
		this.catalogadaGc = catalogadaGc;
	}

	public String getCsi() {
		return csi;
	}

	public void setCsi(String csi) {
		this.csi = csi;
	}

	public String getComEc() {
		return comEc;
	}

	public void setComEc(String comEc) {
		this.comEc = comEc;
	}

	public String getComP() {
		return comP;
	}

	public void setComP(String comP) {
		this.comP = comP;
	}

	public String getChequeraCargo() {
		return chequeraCargo;
	}

	public void setChequeraCargo(String chequeraCargo) {
		this.chequeraCargo = chequeraCargo;
	}

	public Integer getNoProteccion() {
		return noProteccion;
	}

	public void setNoProteccion(Integer noProteccion) {
		this.noProteccion = noProteccion;
	}

	public String getLlaveTemporal() {
		return llaveTemporal;
	}

	public void setLlaveTemporal(String llaveTemporal) {
		this.llaveTemporal = llaveTemporal;
	}

	public Integer getEstatusProteccion() {
		return estatusProteccion;
	}

	public void setEstatusProteccion(Integer estatusProteccion) {
		this.estatusProteccion = estatusProteccion;
	}

	public String getContrato() {
		return contrato;
	}

	public void setContrato(String contrato) {
		this.contrato = contrato;
	}

	public String getfIngreso() {
		return fIngreso;
	}

	public void setfIngreso(String fIngreso) {
		this.fIngreso = fIngreso;
	}

	public Integer getUdemer() {
		return udemer;
	}

	public void setUdemer(Integer udemer) {
		this.udemer = udemer;
	}

	public String getFraqnuiciaRegistro() {
		return fraqnuiciaRegistro;
	}

	public void setFraqnuiciaRegistro(String fraqnuiciaRegistro) {
		this.fraqnuiciaRegistro = fraqnuiciaRegistro;
	}

	public Integer getIdFranquicia() {
		return idFranquicia;
	}

	public void setIdFranquicia(Integer idFranquicia) {
		this.idFranquicia = idFranquicia;
	}

	public String getMc() {
		return mc;
	}

	public void setMc(String mc) {
		this.mc = mc;
	}

}

package com.citi.euces.pronosticos.infra.dto;

public class Maestro_ComisionesBEDTO {
	
	public Long no_cliente;//nocliente
    public String chequera;
    public String sucursal;
    public String cuenta;
    public int id_servicio;
    public int id_ondemand;
    public int mes;
    public int anio;
    public int p_iva;
    public double m_comision;
    public double m_iva;
    public double m_total;
    public double m_com_parcial;
    public double m_iva_parcial;
    public int id_estatus_comision;
    public int id_causa_rechazo;
    public String f_movimiento;
    public String f_registro_contable;
    
    public String Llave;

    public int catalogada_gc;
    public String csi;
    public String com_ec;
    public String com_p;
    public String chequera_cargo;
    public Long no_proteccion;
    public String llave_temporal;
    public String mc;
    public int estatus_proteccion;
    public String contrato;
    public String f_ingreso;
    public int udmer;
    public String franquicia_registro;
    public int id_franquicia;


    public int SEC_ARC;
    public int SEC_INT;

    public int dias;

    public String fechaVencimiento;

    public String keyUp;
    public String contenido;

    public String open_item;
    
	public Maestro_ComisionesBEDTO() {
	}
	
	public Maestro_ComisionesBEDTO(Long no_cliente, String chequera, String sucursal, String cuenta, int id_servicio,
			int id_ondemand, int mes, int anio, int p_iva, double m_comision, double m_iva, double m_total,
			double m_com_parcial, double m_iva_parcial, int id_estatus_comision, int id_causa_rechazo,
			String f_movimiento, String f_registro_contable, String llave, int catalogada_gc, String csi, String com_ec,
			String com_p, String chequera_cargo, Long no_proteccion, String llave_temporal, String mc,
			int estatus_proteccion, String contrato, String f_ingreso, int udmer, String franquicia_registro,
			int id_franquicia) {
		this.no_cliente = no_cliente;
		this.chequera = chequera;
		this.sucursal = sucursal;
		this.cuenta = cuenta;
		this.id_servicio = id_servicio;
		this.id_ondemand = id_ondemand;
		this.mes = mes;
		this.anio = anio;
		this.p_iva = p_iva;
		this.m_comision = m_comision;
		this.m_iva = m_iva;
		this.m_total = m_total;
		this.m_com_parcial = m_com_parcial;
		this.m_iva_parcial = m_iva_parcial;
		this.id_estatus_comision = id_estatus_comision;
		this.id_causa_rechazo = id_causa_rechazo;
		this.f_movimiento = f_movimiento;
		this.f_registro_contable = f_registro_contable;
		Llave = llave;
		this.catalogada_gc = catalogada_gc;
		this.csi = csi;
		this.com_ec = com_ec;
		this.com_p = com_p;
		this.chequera_cargo = chequera_cargo;
		this.no_proteccion = no_proteccion;
		this.llave_temporal = llave_temporal;
		this.mc = mc;
		this.estatus_proteccion = estatus_proteccion;
		this.contrato = contrato;
		this.f_ingreso = f_ingreso;
		this.udmer = udmer;
		this.franquicia_registro = franquicia_registro;
		this.id_franquicia = id_franquicia;
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

	public int getId_servicio() {
		return id_servicio;
	}

	public void setId_servicio(int id_servicio) {
		this.id_servicio = id_servicio;
	}

	public int getId_ondemand() {
		return id_ondemand;
	}

	public void setId_ondemand(int id_ondemand) {
		this.id_ondemand = id_ondemand;
	}

	public int getMes() {
		return mes;
	}

	public void setMes(int mes) {
		this.mes = mes;
	}

	public int getAnio() {
		return anio;
	}

	public void setAnio(int anio) {
		this.anio = anio;
	}

	public int getP_iva() {
		return p_iva;
	}

	public void setP_iva(int p_iva) {
		this.p_iva = p_iva;
	}

	public double getM_comision() {
		return m_comision;
	}

	public void setM_comision(double m_comision) {
		this.m_comision = m_comision;
	}

	public double getM_iva() {
		return m_iva;
	}

	public void setM_iva(double m_iva) {
		this.m_iva = m_iva;
	}

	public double getM_total() {
		return m_total;
	}

	public void setM_total(double m_total) {
		this.m_total = m_total;
	}

	public double getM_com_parcial() {
		return m_com_parcial;
	}

	public void setM_com_parcial(double m_com_parcial) {
		this.m_com_parcial = m_com_parcial;
	}

	public double getM_iva_parcial() {
		return m_iva_parcial;
	}

	public void setM_iva_parcial(double m_iva_parcial) {
		this.m_iva_parcial = m_iva_parcial;
	}

	public int getId_estatus_comision() {
		return id_estatus_comision;
	}

	public void setId_estatus_comision(int id_estatus_comision) {
		this.id_estatus_comision = id_estatus_comision;
	}

	public int getId_causa_rechazo() {
		return id_causa_rechazo;
	}

	public void setId_causa_rechazo(int id_causa_rechazo) {
		this.id_causa_rechazo = id_causa_rechazo;
	}

	public String getF_movimiento() {
		return f_movimiento;
	}

	public void setF_movimiento(String f_movimiento) {
		this.f_movimiento = f_movimiento;
	}

	public String getF_registro_contable() {
		return f_registro_contable;
	}

	public void setF_registro_contable(String f_registro_contable) {
		this.f_registro_contable = f_registro_contable;
	}

	public String getLlave() {
		return Llave;
	}

	public void setLlave(String llave) {
		Llave = llave;
	}

	public int getCatalogada_gc() {
		return catalogada_gc;
	}

	public void setCatalogada_gc(int catalogada_gc) {
		this.catalogada_gc = catalogada_gc;
	}

	public String getCsi() {
		return csi;
	}

	public void setCsi(String csi) {
		this.csi = csi;
	}

	public String getCom_ec() {
		return com_ec;
	}

	public void setCom_ec(String com_ec) {
		this.com_ec = com_ec;
	}

	public String getCom_p() {
		return com_p;
	}

	public void setCom_p(String com_p) {
		this.com_p = com_p;
	}

	public String getChequera_cargo() {
		return chequera_cargo;
	}

	public void setChequera_cargo(String chequera_cargo) {
		this.chequera_cargo = chequera_cargo;
	}

	public Long getNo_proteccion() {
		return no_proteccion;
	}

	public void setNo_proteccion(Long no_proteccion) {
		this.no_proteccion = no_proteccion;
	}

	public String getLlave_temporal() {
		return llave_temporal;
	}

	public void setLlave_temporal(String llave_temporal) {
		this.llave_temporal = llave_temporal;
	}

	public String getMc() {
		return mc;
	}

	public void setMc(String mc) {
		this.mc = mc;
	}

	public int getEstatus_proteccion() {
		return estatus_proteccion;
	}

	public void setEstatus_proteccion(int estatus_proteccion) {
		this.estatus_proteccion = estatus_proteccion;
	}

	public String getContrato() {
		return contrato;
	}

	public void setContrato(String contrato) {
		this.contrato = contrato;
	}

	public String getF_ingreso() {
		return f_ingreso;
	}

	public void setF_ingreso(String f_ingreso) {
		this.f_ingreso = f_ingreso;
	}

	public int getUdmer() {
		return udmer;
	}

	public void setUdmer(int udmer) {
		this.udmer = udmer;
	}

	public String getFranquicia_registro() {
		return franquicia_registro;
	}

	public void setFranquicia_registro(String franquicia_registro) {
		this.franquicia_registro = franquicia_registro;
	}

	public int getId_franquicia() {
		return id_franquicia;
	}

	public void setId_franquicia(int id_franquicia) {
		this.id_franquicia = id_franquicia;
	}

	public int getSEC_ARC() {
		return SEC_ARC;
	}

	public void setSEC_ARC(int sEC_ARC) {
		SEC_ARC = sEC_ARC;
	}

	public int getSEC_INT() {
		return SEC_INT;
	}

	public void setSEC_INT(int sEC_INT) {
		SEC_INT = sEC_INT;
	}

	public int getDias() {
		return dias;
	}

	public void setDias(int dias) {
		this.dias = dias;
	}

	public String getFechaVencimiento() {
		return fechaVencimiento;
	}

	public void setFechaVencimiento(String fechaVencimiento) {
		this.fechaVencimiento = fechaVencimiento;
	}

	public String getKeyUp() {
		return keyUp;
	}

	public void setKeyUp(String keyUp) {
		this.keyUp = keyUp;
	}

	public String getContenido() {
		return contenido;
	}

	public void setContenido(String contenido) {
		this.contenido = contenido;
	}

	public String getOpen_item() {
		return open_item;
	}

	public void setOpen_item(String open_item) {
		this.open_item = open_item;
	}

    
    
}

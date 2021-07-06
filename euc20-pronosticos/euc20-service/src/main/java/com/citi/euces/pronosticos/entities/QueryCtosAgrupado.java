package com.citi.euces.pronosticos.entities;

import java.io.Serializable;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name = "PPC_PCB_QUERY_CTOS_AGRUPADO")
public class QueryCtosAgrupado implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Column(name = "CUENTA")
	private Long cuenta;
	
	@Size(min = 1, max = 50)
	@Column(name = "PREFMDA")
	private String prefmda;
	
	@Size(min = 1, max = 50)
	@Column(name = "CUENTAMDA")
	private String cuentamda;

	@Size(min = 1, max = 50)
	@Column(name = "CVE_ESTATUS")
	private String cveEstatus;
	
	@Size(min = 1, max = 55)
	@Column(name = "NOMBRE")
	private String nombre;
	
	@Size(min = 1, max = 20)
	@Column(name = "USO")
	private String uso;
	
	@Size(min = 1, max = 20)
	@Column(name = "MON")
	private String mon;
	
	@Size(min = 1, max = 25)
	@Column(name = "FRANQUICIA")
	private String franquicia;
	
	@Size(min = 1, max = 20)
	@Column(name = "DUPLICADO")
	private String duplicado;

	@Id
	@Basic(optional = false)
	@NotNull
	@Column(name = "ID")
	private Long id;
	
	public Long getCuenta() {
		return cuenta;
	}

	public void setCuenta(Long cuenta) {
		this.cuenta = cuenta;
	}

	public String getPrefmda() {
		return prefmda;
	}

	public void setPrefmda(String prefmda) {
		this.prefmda = prefmda;
	}

	public String getCuentamda() {
		return cuentamda;
	}

	public void setCuentamda(String cuentamda) {
		this.cuentamda = cuentamda;
	}

	public String getCveEstatus() {
		return cveEstatus;
	}

	public void setCveEstatus(String cveEstatus) {
		this.cveEstatus = cveEstatus;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getUso() {
		return uso;
	}

	public void setUso(String uso) {
		this.uso = uso;
	}

	public String getMon() {
		return mon;
	}

	public void setMon(String mon) {
		this.mon = mon;
	}

	public String getFranquicia() {
		return franquicia;
	}

	public void setFranquicia(String franquicia) {
		this.franquicia = franquicia;
	}

	public String getDuplicado() {
		return duplicado;
	}

	public void setDuplicado(String duplicado) {
		this.duplicado = duplicado;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	
}

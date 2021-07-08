package com.citi.euces.pronosticos.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "PPC_PCB_CAT_CLAVES_PRODUCTOS")
public class CatClavesProductos implements Serializable {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Column(name = "PRODUCTO")
	private String producto;
	@Id @GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "CLAVE")
	private String clave;
	
	public String getProducto() {
		return producto;
	}
	public void setProducto(String producto) {
		this.producto = producto;
	}
	public String getClave() {
		return clave;
	}
	public void setClave(String clave) {
		this.clave = clave;
	}
	
	
	
}

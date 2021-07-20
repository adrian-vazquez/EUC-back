package com.citi.euces.pronosticos.infra.dto;

import java.io.Serializable;

public class CatClavesProductosDTO implements Serializable {

    private static final long serialVersionUID = 1L;
    private Long idClaveProducto;
    private String producto;
    private String clave;

    public CatClavesProductosDTO(Long idClaveProducto, String producto, String clave) {
        this.idClaveProducto = idClaveProducto;
        this.producto = producto;
        this.clave = clave;
    }

    public Long getIdClaveProducto() {
        return idClaveProducto;
    }

    public void setIdClaveProducto(Long idClaveProducto) {
        this.idClaveProducto = idClaveProducto;
    }

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

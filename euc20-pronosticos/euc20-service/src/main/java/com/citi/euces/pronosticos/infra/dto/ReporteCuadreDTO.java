package com.citi.euces.pronosticos.infra.dto;

public interface ReporteCuadreDTO {


    String getTipo();
    void setTipo(String tipo);

    String getNoCliente();
    void setNoCliente(String noCliente);

    String getComision();
    void setComision(Integer comision);

    Double getIva();
    void setIva(Double iva);

    Double getTotal();
    void setTotal(Double total);


}

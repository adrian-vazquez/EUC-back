package com.citi.euces.pronosticos.infra.dto;

public class Cat_Serv_Ondemand_ProBEDTO {
	
	public String servicio;
    public int id_servicio;
    public int id_ondemand;
    
	public Cat_Serv_Ondemand_ProBEDTO() {
	}

	public Cat_Serv_Ondemand_ProBEDTO(String servicio, int id_servicio, int id_ondemand) {
		super();
		this.servicio = servicio;
		this.id_servicio = id_servicio;
		this.id_ondemand = id_ondemand;
	}

	public String getServicio() {
		return servicio;
	}

	public void setServicio(String servicio) {
		this.servicio = servicio;
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
	
	
    
    

}

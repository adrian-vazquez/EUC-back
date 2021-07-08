package com.citi.euces.pronosticos.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "PPC_MIS_REBAJA_PRONOSTICO")
public class RebajaPronostico implements Serializable {

	@Id 
	@Column(name = "NUM_PROTECCION")
	private Long numProtec;

	public Long getNumProtec() {
		return numProtec;
	}

	public void setNumProtec(Long numProtec) {
		this.numProtec = numProtec;
	}
	
	
}

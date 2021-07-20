package com.citi.euces.pronosticos.models;

import java.io.Serializable;

import com.citi.euces.pronosticos.infra.dto.ReportesCobuDTO;

public class ReportesCobuResponse implements Serializable{
	
	private ReportesCobuDTO file;
	private String code;
	
	public ReportesCobuResponse(ReportesCobuDTO file, String code) {
		this.file = file;
		this.code = code;
	}
	public ReportesCobuDTO getFile() {
		return file;
	}
	public void setFile(ReportesCobuDTO file) {
		this.file = file;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	
	
}

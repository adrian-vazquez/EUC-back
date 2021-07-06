package com.citi.euces.pronosticos.infra.dto;

import java.io.Serializable;

public class MessagePronosticos implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private Integer code;
	private String messageConfirm;
	
	/**
	 * @return the code
	 */
	public Integer getCode() {
		return code;
	}
	/**
	 * @param code the code to set
	 */
	public void setCode(Integer code) {
		this.code = code;
	}
	/**
	 * @return the messageConfirm
	 */
	public String getMessageConfirm() {
		return messageConfirm;
	}
	/**
	 * @param messageConfirm the messageConfirm to set
	 */
	public void setMessageConfirm(String messageConfirm) {
		this.messageConfirm = messageConfirm;
	}
	
	
}

/**
 * 
 */
package com.citi.euces.pronosticos.infra.dto;

import java.io.Serializable;

/**
 * @author lbermejo
 * 
 * Clase que representa un Transfer Object para los parametros de entrada de los
 * servicios
 * 
 * */
public class ParamInputTO<T> implements Serializable {

    /** Version del objeto serializable */
    private static final long serialVersionUID = 1L;
    private String sessionID;
    private String usuario;
    private T input;

    /** Constructor por omision */
    public ParamInputTO() {}

    /**
     * 
     * @param input
     */
    public ParamInputTO(T input) {
        this.input = input;
    }
    
    /**
     * 
     * @param sessionID
     * @param input
     */
    public ParamInputTO(String sessionID, T input) {
        this.sessionID = sessionID;
        this.input = input;
    }

    @Override
    public String toString() {
        return "ParamInputTO [sessionID=" + sessionID + 
        		", usuario=" + usuario + ", input=" + input + "]";
    }

	/**
	 * @return the sessionID
	 */
	public String getSessionID() {
		return sessionID;
	}

	/**
	 * @param sessionID the sessionID to set
	 */
	public void setSessionID(String sessionID) {
		this.sessionID = sessionID;
	}

	/**
	 * @return the usuario
	 */
	public String getUsuario() {
		return usuario;
	}

	/**
	 * @param usuario the usuario to set
	 */
	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	/**
	 * @return the input
	 */
	public T getInput() {
		return input;
	}

	/**
	 * @param input the input to set
	 */
	public void setInput(T input) {
		this.input = input;
	}

}

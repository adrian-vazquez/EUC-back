/**
 * 
 */
package com.citi.euces.pronosticos.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @author lbermejo
 *
 */
@ResponseStatus(HttpStatus.NOT_FOUND)
public class OrderNotFoundException extends RuntimeException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public OrderNotFoundException() {}
	public OrderNotFoundException(final String message) {
		super(message);
	}
	
	public OrderNotFoundException(final String message, final Throwable cause) {
		super(message, cause);
	}
	
	public OrderNotFoundException(final Throwable cause) {
		super(cause);
	}
	
}

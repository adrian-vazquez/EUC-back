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
public class UserNotFoundException extends RuntimeException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public UserNotFoundException() {}
	public UserNotFoundException(final String message) {
		super(message);
	}
	
	public UserNotFoundException(final String message, final Throwable cause) {
		super(message, cause);
	}
	
	public UserNotFoundException(final Throwable cause) {
		super(cause);
	}
	
}

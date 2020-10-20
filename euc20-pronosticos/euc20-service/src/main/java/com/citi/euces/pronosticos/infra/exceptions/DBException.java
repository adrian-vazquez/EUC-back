/**
 *
 */
package com.citi.euces.pronosticos.infra.exceptions;


/**
 * @author lbermejo
 *
 */
public class DBException extends RuntimeException{

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	public DBException() {}
	public DBException(final String message) {
		super(message);
	}

	public DBException(final String message, final Throwable cause) {
		super(message, cause);
	}

	public DBException(final Throwable cause) {
		super(cause);
	}

}

/**
 * 
 */
package com.citi.euces.pronosticos.infra.exceptions;

/**
 * 
 * @author lbermejo
 *
 */
public class APPException extends Exception {

    private static final long serialVersionUID = 1L;

    public APPException() {
        super();
    }

    public APPException(String message) {
        super(message);
    }

    public APPException(String message, Throwable cause) {
        super(message, cause);
    }

    public APPException(Throwable cause) {
        super(cause);
    }

}

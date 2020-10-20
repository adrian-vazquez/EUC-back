/**
 *
 */
package com.citi.euces.pronosticos.infra.exceptions;

/**
 * @author lbermejo
 */
//@ApplicationException(rollback = true)
public class APPServiceException extends APPException {

    /**
     * Field serialVersionUID. (value is 1)
     */
    private static final long serialVersionUID = 1L;

    /**
     * Constructor for IDSServiceException.
     */
    public APPServiceException() {
        super();
    }

    /**
     * Constructor for IDSServiceException.
     *
     * @param message
     *            String
     */
    public APPServiceException(String message) {
        super(message);
    }

    /**
     * Constructor for IDSServiceException.
     *
     * @param message
     *            String
     * @param cause
     *            Throwable
     */
    public APPServiceException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Constructor for IDSServiceException.
     *
     * @param cause
     *            Throwable
     */
    public APPServiceException(Throwable cause) {
        super(cause);
    }
}

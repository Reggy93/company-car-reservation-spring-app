package org.reggy93.ccrsa.service.exception;

/**
 * General exception thrown to indicate an error or used for wrapping exceptions thrown in {@code Service} layer.
 *
 * @author Reggy93 <marcin.z.wrobel@gmail.com>
 * created on 21 Jul 2020
 */
public class ServiceOperationException extends Exception {

    public ServiceOperationException(String message) {
        super(message);
    }

    public ServiceOperationException(String message, Throwable cause) {
        super(message, cause);
    }
}

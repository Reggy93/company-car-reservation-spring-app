package org.reggy93.ccrsa.facade.exception;

/**
 * General exception thrown to indicate an error or used for wrapping exceptions thrown in {@code Facade} layer.
 *
 * @author Reggy93 <marcin.z.wrobel@gmail.com>
 * created on 22 Jul 2020
 */
public class FacadeOperationException extends Exception{

    public FacadeOperationException(String message) {
        super(message);
    }

    public FacadeOperationException(String message, Throwable cause) {
        super(message, cause);
    }
}

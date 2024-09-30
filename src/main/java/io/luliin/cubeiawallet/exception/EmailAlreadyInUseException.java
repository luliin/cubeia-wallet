package io.luliin.cubeiawallet.exception;

/**
 * @author Julia Wigenstedt
 * Date: 2024-09-30
 */
public class EmailAlreadyInUseException extends RuntimeException {
    public EmailAlreadyInUseException(String message) {
        super(message);
    }
}

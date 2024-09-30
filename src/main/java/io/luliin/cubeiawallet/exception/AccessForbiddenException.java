package io.luliin.cubeiawallet.exception;

/**
 * @author Julia Wigenstedt
 * Date: 2024-09-30
 */
public class AccessForbiddenException extends RuntimeException {
    public AccessForbiddenException(String message) {
        super(message);
    }
}

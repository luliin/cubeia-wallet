package io.luliin.cubeiawallet.exception;

/**
 * @author Julia Wigenstedt
 * Date: 2024-09-30
 */
public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(String message) {
        super(message);
    }
}

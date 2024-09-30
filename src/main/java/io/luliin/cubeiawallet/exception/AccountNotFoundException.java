package io.luliin.cubeiawallet.exception;

/**
 * @author Julia Wigenstedt
 * Date: 2024-09-30
 */
public class AccountNotFoundException extends RuntimeException {
    public AccountNotFoundException(String message) {
        super(message);
    }
}

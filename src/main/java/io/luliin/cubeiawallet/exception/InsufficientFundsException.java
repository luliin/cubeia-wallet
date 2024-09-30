package io.luliin.cubeiawallet.exception;

/**
 * @author Julia Wigenstedt
 * Date: 2024-09-30
 */
public class InsufficientFundsException extends RuntimeException {
    public InsufficientFundsException(String message) {
        super(message);
    }
}


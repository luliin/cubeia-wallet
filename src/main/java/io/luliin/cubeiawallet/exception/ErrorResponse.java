package io.luliin.cubeiawallet.exception;

import java.time.LocalDateTime;
/**
 * @author Julia Wigenstedt
 * Date: 2024-09-30
 */
public record ErrorResponse(
        String message,
        String path,
        int status,
        LocalDateTime timestamp
) {
    public ErrorResponse(String message, String path, int status) {
        this(message, path, status, LocalDateTime.now());
    }
}


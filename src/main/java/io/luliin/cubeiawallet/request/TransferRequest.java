package io.luliin.cubeiawallet.request;

import java.math.BigDecimal;

/**
 * @author Julia Wigenstedt
 * Date: 2024-09-30
 */
public record TransferRequest(Long fromAccountId, Long toAccountId, Long userId, BigDecimal amount) {
}

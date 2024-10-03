package io.luliin.cubeiawallet.request;

import java.math.BigDecimal;

/**
 * @author Julia Wigenstedt
 * Date: 2024-10-03
 */
public record TransactionRequest(Long accountId, Long userId, BigDecimal amount) {
}

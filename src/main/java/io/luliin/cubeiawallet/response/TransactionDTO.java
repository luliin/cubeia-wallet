package io.luliin.cubeiawallet.response;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * @author Julia Wigenstedt
 * Date: 2024-09-30
 */
public record TransactionDTO(
        Long transactionId,
        AccountDTO account,
        BigDecimal amount,
        LocalDateTime transactionDate
) {
}

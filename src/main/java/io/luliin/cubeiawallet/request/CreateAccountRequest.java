package io.luliin.cubeiawallet.request;

import java.math.BigDecimal;

/**
 * @author Julia Wigenstedt
 * Date: 2024-09-30
 */
public record CreateAccountRequest(Long userId, BigDecimal initialBalance) {
}

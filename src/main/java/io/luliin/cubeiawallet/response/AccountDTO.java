package io.luliin.cubeiawallet.response;

import java.math.BigDecimal;

/**
 * @author Julia Wigenstedt
 * Date: 2024-09-30
 */
public record AccountDTO(Long id, UserDTO user, BigDecimal balance) {
}

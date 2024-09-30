package io.luliin.cubeiawallet.repository;

import io.luliin.cubeiawallet.model.Account;
import io.luliin.cubeiawallet.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

/**
 * @author Julia Wigenstedt
 * Date: 2024-09-30
 */
public interface AccountRepository extends JpaRepository<Account, Long> {
    @Query("SELECT a.balance FROM Account a WHERE a.id = :accountId")
    BigDecimal findBalanceByAccountId(@Param("accountId") Long accountId);
    List<Account> findAccountsByUser(User user);

    boolean existsByUserAndId(User user, Long id);
}

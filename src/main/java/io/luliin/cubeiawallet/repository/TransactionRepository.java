package io.luliin.cubeiawallet.repository;

import io.luliin.cubeiawallet.model.Account;
import io.luliin.cubeiawallet.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @author Julia Wigenstedt
 * Date: 2024-09-30
 */
public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    List<Transaction> findAllByAccount(Account account);
}

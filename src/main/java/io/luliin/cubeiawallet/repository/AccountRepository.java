package io.luliin.cubeiawallet.repository;

import io.luliin.cubeiawallet.model.Account;
import io.luliin.cubeiawallet.model.User;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;

import java.util.Optional;

/**
 * @author Julia Wigenstedt
 * Date: 2024-09-30
 */
public interface AccountRepository extends JpaRepository<Account, Long> {

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    Optional<Account> findById(Long id);

    boolean existsByUserAndId(User user, Long id);
}

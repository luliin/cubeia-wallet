package io.luliin.cubeiawallet.repository;

import io.luliin.cubeiawallet.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author Julia Wigenstedt
 * Date: 2024-09-30
 */
public interface UserRepository extends JpaRepository<User, Long> {

    boolean existsByEmail(String email);
}

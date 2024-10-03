package io.luliin.cubeiawallet.service;

import io.luliin.cubeiawallet.exception.AccessForbiddenException;
import io.luliin.cubeiawallet.exception.AccountNotFoundException;
import io.luliin.cubeiawallet.exception.EmailAlreadyInUseException;
import io.luliin.cubeiawallet.exception.UserNotFoundException;
import io.luliin.cubeiawallet.model.Account;
import io.luliin.cubeiawallet.model.User;
import io.luliin.cubeiawallet.repository.AccountRepository;
import io.luliin.cubeiawallet.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

/**
 * @author Julia Wigenstedt
 * Date: 2024-10-03
 */
@Service
public class ValidationService {

    private final UserRepository userRepository;
    private final AccountRepository accountRepository;

    public ValidationService(UserRepository userRepository, AccountRepository accountRepository) {
        this.userRepository = userRepository;
        this.accountRepository = accountRepository;
    }

    public void validateAccountUser(Long accountId, Long userId) {

        User user = getUserById(userId);

        if (!accountRepository.existsByUserAndId(user, accountId)) {
            throw new AccessForbiddenException("Access forbidden: User does not own the account");
        }
    }

    public User getUserById(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found"));
    }

    public Account getAccountById(Long accountId) {
        return accountRepository.findById(accountId)
                .orElseThrow(() -> new AccountNotFoundException("Account not found"));
    }

    public void validateEmailIsAvailable(String email) {
        if (userRepository.existsByEmail(email)) {
            throw new EmailAlreadyInUseException("Email is already in use");
        }
    }

    public Account getOrCreateAccountForUser(Long accountId, User user) {
        return accountRepository.findById(accountId)
                .orElseGet(() -> createAccountForUser(accountId, user));
    }

    private Account createAccountForUser(Long accountId, User user) {
        Account newAccount = new Account();
        newAccount.setId(accountId);
        newAccount.setUser(user);
        newAccount.setBalance(BigDecimal.ZERO);
        return accountRepository.save(newAccount);
    }
}

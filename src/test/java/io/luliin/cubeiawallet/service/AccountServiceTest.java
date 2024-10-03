package io.luliin.cubeiawallet.service;

import io.luliin.cubeiawallet.model.Account;
import io.luliin.cubeiawallet.model.User;
import io.luliin.cubeiawallet.repository.AccountRepository;
import io.luliin.cubeiawallet.request.CreateAccountRequest;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.mockito.Mockito.*;

/**
 * @author Julia Wigenstedt
 * Date: 2024-10-03
 */
class AccountServiceTest {

    private final AccountRepository accountRepository = mock(AccountRepository.class);
    private final ValidationService validationService = mock(ValidationService.class);

    private final AccountService accountService = new AccountService(accountRepository, validationService);

    @Test
    void testCreateAccount() {
        Long userId = 1L;
        BigDecimal initialBalance = new BigDecimal("100.00");

        CreateAccountRequest createAccountRequest = new CreateAccountRequest(userId, initialBalance);

        User mockUser = new User();
        Account mockAccount = new Account(1L, mockUser, initialBalance);

        when(validationService.getUserById(userId)).thenReturn(mockUser);
        when(accountRepository.save(any(Account.class))).thenReturn(mockAccount);

        accountService.createAccount(createAccountRequest);

        verify(accountRepository, times(1)).save(any(Account.class));
    }
}

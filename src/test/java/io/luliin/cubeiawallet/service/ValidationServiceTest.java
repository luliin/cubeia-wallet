package io.luliin.cubeiawallet.service;

import io.luliin.cubeiawallet.exception.AccessForbiddenException;
import io.luliin.cubeiawallet.exception.AccountNotFoundException;
import io.luliin.cubeiawallet.exception.UserNotFoundException;
import io.luliin.cubeiawallet.model.Account;
import io.luliin.cubeiawallet.model.User;
import io.luliin.cubeiawallet.repository.AccountRepository;
import io.luliin.cubeiawallet.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

/**
 * @author Julia Wigenstedt
 * Date: 2024-10-03
 */

@ExtendWith(MockitoExtension.class)
class ValidationServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private AccountRepository accountRepository;

    @InjectMocks
    private ValidationService validationService;


    @Test
    void validateAccountUser_Success() {
        Long accountId = 1L;
        Long userId = 1L;
        User user = new User();
        Account account = new Account();

        when(userRepository.findById(userId)).thenReturn(java.util.Optional.of(user));
        when(accountRepository.existsByUserAndId(user, accountId)).thenReturn(true);

        assertDoesNotThrow(() -> validationService.validateAccountUser(accountId, userId));
    }

    @Test
    void validateAccountUser_AccessForbidden() {
        Long accountId = 1L;
        Long userId = 1L;
        User user = new User();

        when(userRepository.findById(userId)).thenReturn(java.util.Optional.of(user));
        when(accountRepository.existsByUserAndId(user, accountId)).thenReturn(false);

        assertThrows(AccessForbiddenException.class, () -> validationService.validateAccountUser(accountId, userId));
    }

    @Test
    void getUserById_Success() {
        Long userId = 1L;
        User user = new User();

        when(userRepository.findById(userId)).thenReturn(java.util.Optional.of(user));

        assertEquals(user, validationService.getUserById(userId));
    }

    @Test
    void getUserById_NotFound() {
        Long userId = 1L;

        when(userRepository.findById(userId)).thenReturn(java.util.Optional.empty());

        assertThrows(UserNotFoundException.class, () -> validationService.getUserById(userId));
    }

    @Test
    void getAccountById_NotFound() {
        Long accountId = 1L;

        when(accountRepository.findById(accountId)).thenReturn(java.util.Optional.empty());

        assertThrows(AccountNotFoundException.class, () -> validationService.getAccountById(accountId));
    }
}

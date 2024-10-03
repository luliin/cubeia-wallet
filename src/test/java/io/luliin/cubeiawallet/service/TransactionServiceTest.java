package io.luliin.cubeiawallet.service;

import io.luliin.cubeiawallet.exception.InsufficientFundsException;
import io.luliin.cubeiawallet.model.Account;
import io.luliin.cubeiawallet.model.Transaction;
import io.luliin.cubeiawallet.model.User;
import io.luliin.cubeiawallet.repository.AccountRepository;
import io.luliin.cubeiawallet.repository.TransactionRepository;
import io.luliin.cubeiawallet.request.TransactionRequest;
import io.luliin.cubeiawallet.request.TransferRequest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

/**
 * @author Julia Wigenstedt
 * Date: 2024-10-03
 */
@ExtendWith(MockitoExtension.class)
class TransactionServiceTest {

    @Mock
    private TransactionRepository transactionRepository;

    @Mock
    private ValidationService validationService;

    @Mock
    private AccountRepository accountRepository;

    @InjectMocks
    private TransactionService transactionService;

    @Test
    void testTransferFundsSuccess() {
        Long fromAccountId = 1L;
        Long toAccountId = 2L;
        Long userId = 1L;
        BigDecimal amount = new BigDecimal("1000");
        TransferRequest transferRequest = new TransferRequest(fromAccountId, toAccountId, userId, amount);

        Account fromAccount = new Account();
        fromAccount.setBalance(new BigDecimal("2000"));

        Account toAccount = new Account();
        toAccount.setBalance(new BigDecimal("500"));

        when(validationService.getAccountById(fromAccountId)).thenReturn(fromAccount);
        when(validationService.getAccountById(toAccountId)).thenReturn(toAccount);
        when(validationService.getUserById(userId)).thenReturn(new User());

        transactionService.transferFunds(transferRequest);

        verify(accountRepository, times(1)).save(fromAccount);
        verify(accountRepository, times(1)).save(toAccount);
        verify(transactionRepository, times(2)).save(any(Transaction.class)); // Två transaktioner: från och till
    }

    @Test
    void testTransferFundsInsufficientFunds() {
        Long fromAccountId = 1L;
        Long toAccountId = 2L;
        Long userId = 1L;
        BigDecimal amount = new BigDecimal("1000");
        TransferRequest transferRequest = new TransferRequest(fromAccountId, toAccountId, userId, amount);

        Account fromAccount = new Account();
        fromAccount.setBalance(new BigDecimal("500")); // Otillräckligt saldo

        when(validationService.getAccountById(fromAccountId)).thenReturn(fromAccount);
        when(validationService.getAccountById(toAccountId)).thenReturn(new Account());

        assertThrows(InsufficientFundsException.class, () -> transactionService.transferFunds(transferRequest));

        verify(accountRepository, never()).save(fromAccount);
        verify(accountRepository, never()).save(any(Account.class));
    }

    @Test
    void testWithdrawFundsSuccess() {
        Long accountId = 1L;
        Long userId = 1L;
        BigDecimal withdrawAmount = new BigDecimal("500");
        TransactionRequest transactionRequest = new TransactionRequest(accountId, userId, withdrawAmount);

        Account account = new Account();
        account.setBalance(new BigDecimal("1000"));

        when(validationService.getAccountById(accountId)).thenReturn(account);
        when(validationService.getUserById(userId)).thenReturn(new User());

        transactionService.withdrawFunds(transactionRequest);

        verify(accountRepository, times(1)).save(account);
        verify(transactionRepository, times(1)).save(any(Transaction.class));
    }

    @Test
    void testWithdrawFundsInsufficientFunds() {
        Long accountId = 1L;
        Long userId = 1L;
        BigDecimal withdrawAmount = new BigDecimal("1000");
        TransactionRequest transactionRequest = new TransactionRequest(accountId, userId, withdrawAmount);

        Account account = new Account();
        account.setBalance(new BigDecimal("500")); // Otillräckligt saldo

        when(validationService.getAccountById(accountId)).thenReturn(account);

        assertThrows(InsufficientFundsException.class, () -> transactionService.withdrawFunds(transactionRequest));

        verify(accountRepository, never()).save(account);
        verify(transactionRepository, never()).save(any(Transaction.class));
    }

    @Test
    void testDepositFundsSuccess() {
        Long accountId = 1L;
        Long userId = 1L;
        BigDecimal depositAmount = new BigDecimal("1000");
        TransactionRequest transactionRequest = new TransactionRequest(accountId, userId, depositAmount);

        Account account = new Account();
        account.setBalance(new BigDecimal("1000"));

        when(validationService.getAccountById(accountId)).thenReturn(account);
        when(validationService.getUserById(userId)).thenReturn(new User());

        transactionService.depositFunds(transactionRequest);

        verify(accountRepository, times(1)).save(account);
        verify(transactionRepository, times(1)).save(any(Transaction.class));
    }
}

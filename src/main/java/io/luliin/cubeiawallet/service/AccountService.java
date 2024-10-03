package io.luliin.cubeiawallet.service;

import io.luliin.cubeiawallet.exception.AccessForbiddenException;
import io.luliin.cubeiawallet.exception.AccountNotFoundException;
import io.luliin.cubeiawallet.exception.InsufficientFundsException;
import io.luliin.cubeiawallet.exception.UserNotFoundException;
import io.luliin.cubeiawallet.model.Account;
import io.luliin.cubeiawallet.model.Transaction;
import io.luliin.cubeiawallet.repository.TransactionRepository;
import io.luliin.cubeiawallet.model.User;
import io.luliin.cubeiawallet.repository.AccountRepository;
import io.luliin.cubeiawallet.repository.UserRepository;
import io.luliin.cubeiawallet.request.CreateAccountRequest;
import io.luliin.cubeiawallet.request.TransferRequest;
import io.luliin.cubeiawallet.response.AccountDTO;
import io.luliin.cubeiawallet.response.TransactionDTO;
import jakarta.transaction.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author Julia Wigenstedt
 * Date: 2024-09-30
 */
@Service
public class AccountService {

    private final AccountRepository accountRepository;
    private final ValidationService validationService;

    public AccountService(AccountRepository accountRepository, ValidationService validationService) {
        this.accountRepository = accountRepository;
        this.validationService = validationService;
    }

    public BigDecimal getBalance(Long accountId, Long userId) {
        validationService.validateAccountUser(accountId, userId);
        return accountRepository.findBalanceByAccountId(accountId);
    }

    public AccountDTO createAccount(CreateAccountRequest createAccountRequest) {
        validationService.getUserById(createAccountRequest.userId());

        // Skapa nytt konto
        Account newAccount = new Account();
        newAccount.setUser(validationService.getUserById(createAccountRequest.userId()));
        newAccount.setBalance(createAccountRequest.initialBalance() != null ?
                createAccountRequest.initialBalance() : BigDecimal.ZERO);

        return accountRepository.save(newAccount).toDTO();
    }

}

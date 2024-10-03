package io.luliin.cubeiawallet.service;

import io.luliin.cubeiawallet.model.Account;
import io.luliin.cubeiawallet.repository.AccountRepository;
import io.luliin.cubeiawallet.request.CreateAccountRequest;
import io.luliin.cubeiawallet.response.AccountDTO;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

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

        Account newAccount = new Account();
        newAccount.setUser(validationService.getUserById(createAccountRequest.userId()));
        newAccount.setBalance(createAccountRequest.initialBalance() != null ?
                createAccountRequest.initialBalance() : BigDecimal.ZERO);

        return accountRepository.save(newAccount).toDTO();
    }

}

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
    private final UserRepository userRepository;
    private final TransactionRepository transactionRepository;

    public AccountService(AccountRepository accountRepository, UserRepository userRepository, TransactionRepository transactionRepository) {
        this.accountRepository = accountRepository;
        this.userRepository = userRepository;
        this.transactionRepository = transactionRepository;
    }

    public BigDecimal getBalance(Long accountId, Long userId) {

        validateAccountUser(accountId, userId);

        return accountRepository.findBalanceByAccountId(accountId);
    }


    public List<TransactionDTO> getTransactionHistory(Long accountId, Long userId) {

        validateAccountUser(accountId, userId);

        // .get() is safe, because the above method will throw if there is no account
        Account account = accountRepository.findById(accountId).get();

        return transactionRepository.findAllByAccount(account).stream()
                .map(Transaction::toDTO)
                .toList();
    }

    @Transactional
    public void transferFunds(TransferRequest transferRequest) {
        validateAccountUser(transferRequest.fromAccountId(), transferRequest.userId());

        User performedBy = userRepository.findById(transferRequest.userId())
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        Account fromAccount = accountRepository.findById(transferRequest.fromAccountId())
                .orElseThrow(() -> new AccountNotFoundException("From account not found"));

        // Implicitly create account when performing a transaction, for easier testing of the API.
        // This scenario is however highly irregular and should never be implemented IRL.
        Account toAccount = accountRepository.findById(transferRequest.toAccountId())
                .orElseGet(() -> createAccountForUser(transferRequest.toAccountId(), performedBy));

        // Check for sufficient funds
        if (fromAccount.getBalance().compareTo(transferRequest.amount()) < 0) {
            throw new InsufficientFundsException("Insufficient funds");
        }

        fromAccount.setBalance(fromAccount.getBalance().subtract(transferRequest.amount()));
        toAccount.setBalance(toAccount.getBalance().add(transferRequest.amount()));

        accountRepository.save(fromAccount);
        accountRepository.save(toAccount);

        Transaction fromTransaction = new Transaction(fromAccount, transferRequest.amount().negate(), performedBy);
        Transaction toTransaction = new Transaction(toAccount, transferRequest.amount(), performedBy);

        transactionRepository.save(fromTransaction);
        transactionRepository.save(toTransaction);
    }

    private Account createAccountForUser(Long accountId, User user) {
        Account newAccount = new Account();
        newAccount.setId(accountId);
        newAccount.setUser(user);
        newAccount.setBalance(BigDecimal.ZERO);

        return accountRepository.save(newAccount);
    }

    private void validateAccountUser(Long accountId, Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        if (!accountRepository.existsByUserAndId(user, accountId)) {
            throw new AccessForbiddenException("Access forbidden: User does not own the account");
        }
    }

    public AccountDTO createAccount(CreateAccountRequest createAccountRequest) {
        User user = userRepository.findById(createAccountRequest.userId())
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        // Skapa nytt konto
        Account newAccount = new Account();
        newAccount.setUser(user);
        newAccount.setBalance(createAccountRequest.initialBalance() != null ?
                createAccountRequest.initialBalance() : BigDecimal.ZERO);

        return accountRepository.save(newAccount).toDTO();
    }
}

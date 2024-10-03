package io.luliin.cubeiawallet.service;

import io.luliin.cubeiawallet.exception.InsufficientFundsException;
import io.luliin.cubeiawallet.model.Account;
import io.luliin.cubeiawallet.model.Transaction;
import io.luliin.cubeiawallet.model.User;
import io.luliin.cubeiawallet.repository.AccountRepository;
import io.luliin.cubeiawallet.repository.TransactionRepository;
import io.luliin.cubeiawallet.request.TransactionRequest;
import io.luliin.cubeiawallet.request.TransferRequest;
import io.luliin.cubeiawallet.response.TransactionDTO;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
/**
 * @author Julia Wigenstedt
 * Date: 2024-10-03
 */
@Service
public class TransactionService {

    private final TransactionRepository transactionRepository;
    private final ValidationService validationService;
    private final AccountRepository accountRepository;

    public TransactionService(TransactionRepository transactionRepository, ValidationService validationService, AccountRepository accountRepository) {
        this.transactionRepository = transactionRepository;
        this.validationService = validationService;
        this.accountRepository = accountRepository;
    }

    @Transactional
    public void transferFunds(TransferRequest transferRequest) {
        validationService.validateAccountUser(transferRequest.fromAccountId(), transferRequest.userId());

        User performedBy = validationService.getUserById(transferRequest.userId());
        Account fromAccount = validationService.getAccountById(transferRequest.fromAccountId());
        Account toAccount = validationService.getAccountById(transferRequest.toAccountId());

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

    public List<TransactionDTO> getTransactionHistory(Long accountId, Long userId) {
        validationService.validateAccountUser(accountId, userId);
        Account account = validationService.getAccountById(accountId);
        return transactionRepository.findAllByAccount(account).stream()
                .map(Transaction::toDTO)
                .toList();
    }

    @Transactional
    public void withdrawFunds(TransactionRequest transactionRequest) {
        Account account = validationService.getAccountById(transactionRequest.accountId());
        validationService.validateAccountUser(account.getId(), transactionRequest.userId());

        if (account.getBalance().compareTo(transactionRequest.amount()) < 0) {
            throw new InsufficientFundsException("Insufficient funds");
        }

        account.setBalance(account.getBalance().subtract(transactionRequest.amount()));
        Transaction transaction = new Transaction(account, transactionRequest.amount().negate(), validationService.getUserById(transactionRequest.userId()));
        transactionRepository.save(transaction);
    }

    @Transactional
    public void depositFunds(TransactionRequest transactionRequest) {
        Account account = validationService.getAccountById(transactionRequest.accountId());
        validationService.validateAccountUser(account.getId(), transactionRequest.userId());

        account.setBalance(account.getBalance().add(transactionRequest.amount()));
        Transaction transaction = new Transaction(account, transactionRequest.amount(), validationService.getUserById(transactionRequest.userId()));
        transactionRepository.save(transaction);
    }
}


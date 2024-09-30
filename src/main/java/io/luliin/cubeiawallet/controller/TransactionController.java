package io.luliin.cubeiawallet.controller;

import io.luliin.cubeiawallet.response.TransactionDTO;
import io.luliin.cubeiawallet.service.AccountService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author Julia Wigenstedt
 * Date: 2024-09-30
 */
@RestController
@RequestMapping("api/v1/transactions")
public class TransactionController {

    private final AccountService accountService;

    public TransactionController(AccountService accountService) {
        this.accountService = accountService;
    }

    @GetMapping("/{accountId}")
    public ResponseEntity<List<TransactionDTO>> getTransactionHistory(@PathVariable Long accountId, @RequestParam Long userId) {
        return ResponseEntity.ok(accountService.getTransactionHistory(accountId, userId));
    }

}

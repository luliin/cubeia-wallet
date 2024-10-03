package io.luliin.cubeiawallet.controller;

import io.luliin.cubeiawallet.request.TransactionRequest;
import io.luliin.cubeiawallet.request.TransferRequest;
import io.luliin.cubeiawallet.response.TransactionDTO;
import io.luliin.cubeiawallet.service.TransactionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.util.List;

/**
 * @author Julia Wigenstedt
 * Date: 2024-09-30
 */
@RestController
@RequestMapping("api/v1/transactions")
@Tag(name = "Transactions API", description = "API for managing transactions")
public class TransactionController {

    private final TransactionService transactionService;
    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @Operation(summary = "Withdraw funds", description = "Withdraws funds from the specified account.")
    @PutMapping("/withdraw")
    public ResponseEntity<Void> withdrawFunds(@RequestBody TransactionRequest transactionRequest) {
        transactionService.withdrawFunds(transactionRequest);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Deposit funds", description = "Deposits funds to the specified account.")
    @PutMapping("/deposit")
    public ResponseEntity<Void> depositFunds(@RequestBody TransactionRequest transactionRequest) {
        transactionService.depositFunds(transactionRequest);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Transfer funds", description = "Transfers funds between accounts.")
    @PutMapping("/transfer")
    public ResponseEntity<Void> transferFunds(@RequestBody TransferRequest transferRequest) {
        transactionService.transferFunds(transferRequest);
        return ResponseEntity.ok().build();
    }
    @Operation(summary = "List transactions", description = "Lists all transactions for the specified account.")
    @GetMapping("/{accountId}")
    public ResponseEntity<List<TransactionDTO>> getTransactionHistory(@PathVariable Long accountId, @RequestParam Long userId) {
        return ResponseEntity.ok(transactionService.getTransactionHistory(accountId, userId));
    }

}

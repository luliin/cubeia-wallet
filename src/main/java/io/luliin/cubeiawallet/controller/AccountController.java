package io.luliin.cubeiawallet.controller;

import io.luliin.cubeiawallet.request.CreateAccountRequest;
import io.luliin.cubeiawallet.response.AccountDTO;
import io.luliin.cubeiawallet.service.AccountService;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

/**
 * @author Julia Wigenstedt
 * Date: 2024-09-30
 */
@RestController
@RequestMapping("api/v1/account")
@Tag(name = "Account", description = "API for managing accounts")
public class AccountController {

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    private final AccountService accountService;

    @Operation(summary = "Create an account", description = "Creates a new account for the specified user.")
    @PostMapping("/create")
    public ResponseEntity<AccountDTO> createAccount(@RequestBody CreateAccountRequest createAccountRequest) {
        AccountDTO newAccount = accountService.createAccount(createAccountRequest);
        return new ResponseEntity<>(newAccount, HttpStatus.CREATED);
    }

    @Operation(summary = "Get account balance", description = "Returns the balance for the specified account and user.")
    @GetMapping("/balance/{accountId}")
    public ResponseEntity<BigDecimal> getBalance(@PathVariable Long accountId, @RequestParam Long userId) {
        return ResponseEntity.ok(accountService.getBalance(accountId, userId));
    }

}

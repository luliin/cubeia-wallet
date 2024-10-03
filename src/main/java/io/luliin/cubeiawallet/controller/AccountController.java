package io.luliin.cubeiawallet.controller;

import io.luliin.cubeiawallet.request.CreateAccountRequest;
import io.luliin.cubeiawallet.request.TransferRequest;
import io.luliin.cubeiawallet.response.AccountDTO;
import io.luliin.cubeiawallet.service.AccountService;
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
public class AccountController {

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    private final AccountService accountService;

    @PostMapping("/create")
    public ResponseEntity<AccountDTO> createAccount(@RequestBody CreateAccountRequest createAccountRequest) {
        AccountDTO newAccount = accountService.createAccount(createAccountRequest);
        return new ResponseEntity<>(newAccount, HttpStatus.CREATED);
    }

    @GetMapping("/balance/{accountId}")
    public ResponseEntity<BigDecimal> getBalance(@PathVariable Long accountId, @RequestParam Long userId) {
        return ResponseEntity.ok(accountService.getBalance(accountId, userId));
    }

}

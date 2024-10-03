package io.luliin.cubeiawallet.controller;

import io.luliin.cubeiawallet.request.CreateAccountRequest;
import io.luliin.cubeiawallet.response.AccountDTO;
import io.luliin.cubeiawallet.service.AccountService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

/**
 * @author Julia Wigenstedt
 * Date: 2024-10-03
 */
@ExtendWith(MockitoExtension.class)
class AccountControllerTest {

    @Mock
    private AccountService accountService;

    @InjectMocks
    private AccountController accountController;


    @Test
    void createAccountReturnsCreatedAccount() {
        CreateAccountRequest createAccountRequest = new CreateAccountRequest(1L, BigDecimal.valueOf(1000));
        AccountDTO accountDTO = new AccountDTO(1L, null, BigDecimal.valueOf(1000));
        when(accountService.createAccount(createAccountRequest)).thenReturn(accountDTO);

        ResponseEntity<AccountDTO> response = accountController.createAccount(createAccountRequest);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(accountDTO, response.getBody());
        verify(accountService, times(1)).createAccount(createAccountRequest);
    }

    @Test
    void getBalanceReturnsBalanceForAccount() {
        Long accountId = 1L;
        Long userId = 1L;
        BigDecimal balance = BigDecimal.valueOf(1000);
        when(accountService.getBalance(accountId, userId)).thenReturn(balance);

        ResponseEntity<BigDecimal> response = accountController.getBalance(accountId, userId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(balance, response.getBody());
        verify(accountService, times(1)).getBalance(accountId, userId);
    }
}
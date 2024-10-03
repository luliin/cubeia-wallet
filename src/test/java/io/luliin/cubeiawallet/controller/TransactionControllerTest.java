package io.luliin.cubeiawallet.controller;

import io.luliin.cubeiawallet.request.TransactionRequest;
import io.luliin.cubeiawallet.request.TransferRequest;
import io.luliin.cubeiawallet.response.TransactionDTO;
import io.luliin.cubeiawallet.service.TransactionService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

/**
 * @author Julia Wigenstedt
 * Date: 2024-10-03
 */
@ExtendWith(MockitoExtension.class)
class TransactionControllerTest {

    @Mock
    private TransactionService transactionService;

    @InjectMocks
    private TransactionController transactionController;


    @Test
    void withdrawFundsSuccess() {
        TransactionRequest transactionRequest = new TransactionRequest(1L, 1L, null);

        ResponseEntity<Void> response = transactionController.withdrawFunds(transactionRequest);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(transactionService, times(1)).withdrawFunds(transactionRequest);
    }

    @Test
    void depositFundsSuccess() {
        TransactionRequest transactionRequest = new TransactionRequest(1L, 1L, null);

        ResponseEntity<Void> response = transactionController.depositFunds(transactionRequest);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(transactionService, times(1)).depositFunds(transactionRequest);
    }

    @Test
    void transferFundsSuccess() {
        TransferRequest transferRequest = new TransferRequest(1L, 2L, 1L, null);

        ResponseEntity<Void> response = transactionController.transferFunds(transferRequest);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(transactionService, times(1)).transferFunds(transferRequest);
    }

    @Test
    void getTransactionHistorySuccess() {
        Long accountId = 1L;
        Long userId = 1L;
        List<TransactionDTO> transactions = List.of();
        when(transactionService.getTransactionHistory(accountId, userId)).thenReturn(transactions);

        ResponseEntity<List<TransactionDTO>> response = transactionController.getTransactionHistory(accountId, userId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(transactions, response.getBody());
        verify(transactionService, times(1)).getTransactionHistory(accountId, userId);
    }
}
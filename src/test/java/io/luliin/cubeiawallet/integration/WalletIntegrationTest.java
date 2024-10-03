package io.luliin.cubeiawallet.integration;

import io.luliin.cubeiawallet.request.CreateAccountRequest;
import io.luliin.cubeiawallet.request.CreateUserRequest;
import io.luliin.cubeiawallet.request.TransactionRequest;
import io.luliin.cubeiawallet.response.AccountDTO;
import io.luliin.cubeiawallet.response.UserDTO;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;

import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ActiveProfiles;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
/**
 * @author Julia Wigenstedt
 * Date: 2024-10-03
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
class WalletIntegrationTest {

    @LocalServerPort
    private int port;

    private final TestRestTemplate restTemplate = new TestRestTemplate();

    @Test
    void createUserAndAccountSuccess() {
        CreateUserRequest createUserRequest = new CreateUserRequest("test@example.com");
        String userUrl = "http://localhost:" + port + "/api/v1/user";

        HttpEntity<CreateUserRequest> userEntity = new HttpEntity<>(createUserRequest);
        var userResponse = restTemplate.exchange(userUrl, HttpMethod.POST, userEntity, UserDTO.class);
        assertEquals(HttpStatus.OK, userResponse.getStatusCode());
        Long userId = userResponse.getBody().id();

        CreateAccountRequest createAccountRequest = new CreateAccountRequest(userId, null);
        String accountUrl = "http://localhost:" + port + "/api/v1/account/create";
        HttpEntity<CreateAccountRequest> accountEntity = new HttpEntity<>(createAccountRequest);
        var accountResponse = restTemplate.exchange(accountUrl, HttpMethod.POST, accountEntity, AccountDTO.class);
        assertEquals(HttpStatus.CREATED, accountResponse.getStatusCode());
    }

    @Test
    void getBalanceSuccess() {
        CreateUserRequest createUserRequest = new CreateUserRequest("test_balance@example.com");
        String userUrl = "http://localhost:" + port + "/api/v1/user";

        HttpEntity<CreateUserRequest> userEntity = new HttpEntity<>(createUserRequest);
        var userResponse = restTemplate.exchange(userUrl, HttpMethod.POST, userEntity, UserDTO.class);
        assertEquals(HttpStatus.OK, userResponse.getStatusCode());
        Long userId = userResponse.getBody().id();

        CreateAccountRequest createAccountRequest = new CreateAccountRequest(userId, new BigDecimal("500.00"));
        String accountUrl = "http://localhost:" + port + "/api/v1/account/create";
        HttpEntity<CreateAccountRequest> accountEntity = new HttpEntity<>(createAccountRequest);
        var accountResponse = restTemplate.exchange(accountUrl, HttpMethod.POST, accountEntity, AccountDTO.class);
        assertEquals(HttpStatus.CREATED, accountResponse.getStatusCode());

        Long accountId = accountResponse.getBody().id();
        String balanceUrl = "http://localhost:" + port + "/api/v1/account/balance/" + accountId + "?userId=" + userId;

        var balanceResponse = restTemplate.exchange(balanceUrl, HttpMethod.GET, null, BigDecimal.class);
        assertEquals(HttpStatus.OK, balanceResponse.getStatusCode());
        assertEquals(new BigDecimal("500.00"), balanceResponse.getBody());
    }

    @Test
    void depositFundsSuccess() {
        CreateUserRequest createUserRequest = new CreateUserRequest("test_deposit@example.com");
        String userUrl = "http://localhost:" + port + "/api/v1/user";

        HttpEntity<CreateUserRequest> userEntity = new HttpEntity<>(createUserRequest);
        var userResponse = restTemplate.exchange(userUrl, HttpMethod.POST, userEntity, UserDTO.class);
        assertEquals(HttpStatus.OK, userResponse.getStatusCode());
        Long userId = userResponse.getBody().id();

        CreateAccountRequest createAccountRequest = new CreateAccountRequest(userId, new BigDecimal("100.00"));
        String accountUrl = "http://localhost:" + port + "/api/v1/account/create";
        HttpEntity<CreateAccountRequest> accountEntity = new HttpEntity<>(createAccountRequest);
        var accountResponse = restTemplate.exchange(accountUrl, HttpMethod.POST, accountEntity, AccountDTO.class);
        assertEquals(HttpStatus.CREATED, accountResponse.getStatusCode());

        Long accountId = accountResponse.getBody().id();

        TransactionRequest depositRequest = new TransactionRequest(accountId, userId, new BigDecimal("200.00"));
        String depositUrl = "http://localhost:" + port + "/api/v1/transactions/deposit";
        HttpEntity<TransactionRequest> depositEntity = new HttpEntity<>(depositRequest);
        var depositResponse = restTemplate.exchange(depositUrl, HttpMethod.PUT, depositEntity, Void.class);
        assertEquals(HttpStatus.OK, depositResponse.getStatusCode());

        String balanceUrl = "http://localhost:" + port + "/api/v1/account/balance/" + accountId + "?userId=" + userId;
        var balanceResponse = restTemplate.exchange(balanceUrl, HttpMethod.GET, null, BigDecimal.class);
        assertEquals(HttpStatus.OK, balanceResponse.getStatusCode());
        assertEquals(new BigDecimal("300.00"), balanceResponse.getBody());
    }
}

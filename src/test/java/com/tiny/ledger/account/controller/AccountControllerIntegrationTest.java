package com.tiny.ledger.account.controller;

import com.tiny.ledger.account.dto.AccountCreationRequest;
import com.tiny.ledger.account.dto.AccountResponse;
import com.tiny.ledger.transaction.domain.TransactionType;
import com.tiny.ledger.transaction.dto.TransactionRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.client.RestTestClient;
import org.springframework.web.context.WebApplicationContext;

import java.math.BigDecimal;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;


@SpringBootTest
class AccountControllerIntegrationTest {

    private RestTestClient restTestClient;

    @Autowired
    private WebApplicationContext context;

    @BeforeEach
    void setUp() {
        this.restTestClient = RestTestClient.bindToApplicationContext(context).build();
    }

    @Test
    void shouldCreateAccount_returnsAccountResponse(){
        AccountCreationRequest request = new AccountCreationRequest("Phillip", BigDecimal.ZERO);

        restTestClient.post()
                .uri("/v1/accounts")
                .contentType(MediaType.APPLICATION_JSON)
                .body(request)
                .exchange()
                .expectStatus().isCreated()
                .expectBody(AccountResponse.class)
                .value(accountResponse -> assertThat(accountResponse.name()).isEqualTo("Phillip"));

    }

    @Test
    void shouldGetAccountById_returnsAccount() {
        // Cria primeiro

        AccountCreationRequest request = new AccountCreationRequest("Phillip",BigDecimal.ZERO);
        restTestClient.post()
                .uri("/v1/accounts")
                .contentType(MediaType.APPLICATION_JSON)
                .body(request)
                .exchange();

        restTestClient.get()
                .uri("/v1/accounts/1")
                .exchange()
                .expectStatus().isOk()
                .expectBody(AccountResponse.class)
                .value(acc -> assertThat(acc.name()).isEqualTo("Phillip"));
    }

    @Test
    void shouldCreateTransaction_updatesBalance() {
        // Setup

        AccountCreationRequest accountCreationRequest = new AccountCreationRequest("Phillip",BigDecimal.ZERO);

        restTestClient.post().uri("/v1/accounts")
                .body(accountCreationRequest).exchange();

        // Deposit
        TransactionRequest depositRequest  = new TransactionRequest(BigDecimal.valueOf(100, 2), TransactionType.DEPOSIT);
        restTestClient.post().uri("/v1/accounts/1/transactions")
                .contentType(MediaType.APPLICATION_JSON)
                .body(depositRequest)
                .exchange()
                .expectStatus().isCreated();

        TransactionRequest withdrawRequest  = new TransactionRequest(BigDecimal.valueOf(50, 2), TransactionType.WITHDRAW);
        restTestClient.post().uri("/v1/accounts/1/transactions")
                .contentType(MediaType.APPLICATION_JSON)
                .body(withdrawRequest)
                .exchange()
                .expectStatus().isCreated();

        // Verify balance + transaction creation
        restTestClient.get().uri("/v1/accounts/1")
                .exchange()
                .expectStatus().isOk()
                .expectBody(AccountResponse.class)
                .value(acc -> assertThat(acc.balance()).isEqualTo(BigDecimal.valueOf(50, 2)));
    }
}
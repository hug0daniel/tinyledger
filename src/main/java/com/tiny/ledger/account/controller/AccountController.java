package com.tiny.ledger.account.controller;

import com.tiny.ledger.account.dto.AccountCreationRequest;
import com.tiny.ledger.account.dto.AccountResponse;
import com.tiny.ledger.account.service.AccountService;
import com.tiny.ledger.transaction.dto.TransactionRequest;
import com.tiny.ledger.transaction.service.TransactionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/accounts")
public class AccountController {

    private final AccountService accountService;
    private final TransactionService transactionService;

    public AccountController(AccountService accountService, TransactionService transactionService) {
        this.accountService = accountService;
        this.transactionService = transactionService;
    }

    // Endpoint for UserAccount creation
    @PostMapping
    public ResponseEntity<AccountResponse> createAccount(@RequestBody AccountCreationRequest accountCreationRequest){
        return ResponseEntity.status(HttpStatus.CREATED).body(accountService.createAccount(accountCreationRequest));
    }

    // Endpoint to view balance history of the respective User
    @GetMapping("{accountId}")
    public ResponseEntity<AccountResponse> getAccountBalance(@PathVariable Long accountId){

        return ResponseEntity.status(HttpStatus.OK).body(accountService.getAccountById(accountId));
    }

    // Endpoint for UserAccount creation
    @PostMapping("/{accountId}/transactions")
    public ResponseEntity<AccountResponse> postTransaction(@PathVariable Long accountId, @RequestBody TransactionRequest transactionRequest){
        return ResponseEntity.status(HttpStatus.CREATED).body(transactionService.saveTransaction(accountId, transactionRequest));
    }

    @GetMapping("/{accountId}/transactions")
    public ResponseEntity<AccountResponse> getTransactionHistory(@PathVariable Long accountId){
        return ResponseEntity.status(HttpStatus.CREATED).body(transactionService.getAllTransactions(accountId));
    }


}

package com.tiny.ledger.account;

import com.tiny.ledger.account.dto.AccountCreationRequest;
import com.tiny.ledger.account.dto.AccountResponse;
import com.tiny.ledger.account.service.AccountService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class AccountController {

    private final AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    // Endpoint for UserAccount creation
    @PostMapping("/v1/account")
    public ResponseEntity<AccountResponse> createAccount(@RequestBody AccountCreationRequest accountCreationRequest){
        return ResponseEntity.status(HttpStatus.CREATED).body(accountService.createAccount(accountCreationRequest));
    }

    // Endpoint to view balance history of the respective User
    @GetMapping("/v1/account/{accountId}")
    public ResponseEntity<AccountResponse> getAccountBalance(@PathVariable Long accountId){

        return ResponseEntity.status(HttpStatus.OK).body(accountService.getAccountById(accountId));
    }


}

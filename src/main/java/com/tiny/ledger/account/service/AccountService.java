package com.tiny.ledger.account.service;

import com.tiny.ledger.account.dto.AccountResponse;
import com.tiny.ledger.account.dto.AccountCreationRequest;
import org.springframework.stereotype.Service;

@Service
public interface AccountService {

    AccountResponse createAccount(AccountCreationRequest accountCreationRequest);
    AccountResponse getAccountById(Long userId);
}

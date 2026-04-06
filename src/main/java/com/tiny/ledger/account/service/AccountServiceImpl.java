package com.tiny.ledger.account.service;

import com.tiny.ledger.account.domain.Account;
import com.tiny.ledger.account.dto.AccountResponse;
import com.tiny.ledger.account.dto.AccountCreationRequest;
import com.tiny.ledger.account.repository.AccountRepository;
import com.tiny.ledger.shared.Amount;
import org.springframework.stereotype.Component;
import java.util.Optional;

@Component
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;

    public AccountServiceImpl(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }


    @Override
    public AccountResponse createAccount(AccountCreationRequest accountCreationRequest) {
        Account account = mapToAccount(accountCreationRequest);
        accountRepository.saveAccount(account);

        return mapAccountToResponse(account);
    }

    @Override
    public AccountResponse getAccountById(Long userId) {

        if (userId == null) {
            throw new RuntimeException("UserId cannot be null!");
        }
        Optional<Account> account = accountRepository.getAccountById(userId);
        if (account.isEmpty()){
            throw new IllegalArgumentException("There is no such user with id: " + userId);
        }

        return new AccountResponse(
                account.get().getId(),
                account.get().getName(),
                account.get().getBalance().getValue()
        );
    }


    private AccountResponse mapAccountToResponse(Account account) {
        return new AccountResponse(account.getId(), account.getName(), account.getBalance().getValue());
    }

    private Account mapToAccount(AccountCreationRequest accountCreationRequest) {
        Amount amount = new Amount(accountCreationRequest.balance());
        return new Account(null,amount, accountCreationRequest.name());
    }
}

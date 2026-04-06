package com.tiny.ledger.account.repository;

import com.tiny.ledger.account.domain.Account;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AccountRepository {

    void saveAccount(Account account);
    Optional<Account> getAccountById(Long userId);
}

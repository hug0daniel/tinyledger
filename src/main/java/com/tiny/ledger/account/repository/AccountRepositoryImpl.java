package com.tiny.ledger.account.repository;

import com.tiny.ledger.account.Account;
import org.springframework.stereotype.Component;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

@Component
public class AccountRepositoryImpl implements AccountRepository {
    private final Map<Long, Account> accountMap = new HashMap<>();
    private final AtomicLong idCounter = new AtomicLong(0);

    @Override
    public void saveAccount(Account account) {
        if (account.getId() == null) {
            account.setId(idCounter.incrementAndGet());
        }
        accountMap.put(account.getId(),account);
    }

    @Override
    public Optional<Account> getAccountById(Long userId) {
        if (!accountMap.containsKey(userId)) {
            return Optional.empty();
        }
        return Optional.of(accountMap.get(userId));
    }
}

package com.tiny.ledger.transaction.repository;


import com.tiny.ledger.transaction.domain.Transaction;
import org.springframework.stereotype.Component;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

@Component
public class TransactionRepositoryImpl implements TransactionRepository {
    private final Map<Long, Transaction> transactionsMap = new HashMap<>();
    private final AtomicLong idCounter = new AtomicLong(0);

    @Override
    public void saveTransaction(Transaction transaction) {
        if (transaction.getId() == null) {
            transaction.setId(idCounter.incrementAndGet());
        }
        transactionsMap.put(transaction.getId(),transaction);

    }

    @Override
    public List<Transaction> findByAccountId(Long userId) {
        return transactionsMap.values().stream()
                .filter(t -> t.getAccount().getId().equals(userId))
                .toList();

    }
}

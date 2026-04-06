package com.tiny.ledger.transaction.repository;

import com.tiny.ledger.transaction.domain.Transaction;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface TransactionRepository {

    void saveTransaction(Transaction transaction);
    List<Transaction> findByAccountId(Long id);
}

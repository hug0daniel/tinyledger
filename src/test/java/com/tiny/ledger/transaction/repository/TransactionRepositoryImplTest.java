package com.tiny.ledger.transaction.repository;

import com.tiny.ledger.account.domain.Account;
import com.tiny.ledger.shared.Amount;
import com.tiny.ledger.transaction.domain.Transaction;
import com.tiny.ledger.transaction.domain.TransactionType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class TransactionRepositoryImplTest {

    private TransactionRepositoryImpl repository;

    @BeforeEach
    void setUp() {
        repository = new TransactionRepositoryImpl();
    }

    @Test
    void saveTransaction_shouldAssignIdAndPersist_whenIdIsNull() {
        Account account = new Account(1L,new Amount(100.0),"User1");
        Transaction transaction = new Transaction(null, new Amount(100.0), TransactionType.DEPOSIT, account);

        repository.saveTransaction(transaction);

        assertNotNull(transaction.getId());
        assertEquals(1L, transaction.getId());
    }

    @Test
    void getAllTransactions_shouldReturnOnlyTransactionsForGivenUser() {
        Account accountUser1 = new Account(1L,new Amount(100.0),"User1");
        Account accountUser2 = new Account(2L,new Amount(100.0),"User2");

        Transaction t1 = new Transaction(null, new Amount(100.0), TransactionType.DEPOSIT, accountUser1);
        Transaction t2 = new Transaction(null, new Amount(50.0), TransactionType.WITHDRAW, accountUser1);
        Transaction t3 = new Transaction(null, new Amount(200.0), TransactionType.DEPOSIT, accountUser2);

        repository.saveTransaction(t1);
        repository.saveTransaction(t2);
        repository.saveTransaction(t3);

        List<Transaction> result = repository.findByAccountId(1L);

        assertEquals(2, result.size());
        assertTrue(result.stream().allMatch(t -> t.getAccount().getId().equals(1L)));
    }
}
package com.tiny.ledger.transaction.utils;

import com.tiny.ledger.account.domain.Account;
import com.tiny.ledger.shared.Amount;
import com.tiny.ledger.transaction.domain.Transaction;
import com.tiny.ledger.transaction.domain.TransactionType;
import com.tiny.ledger.transaction.dto.TransactionRequest;
import com.tiny.ledger.transaction.dto.TransactionResponse;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class TransactionMapperTest {

    // ─── toTransaction ─────────────────────────────────────────────

    @Test
    void toTransaction_shouldMapRequestToTransaction() {
        Account account = new Account(1L, new Amount(500.0), "John");
        TransactionRequest request = new TransactionRequest(100.0, TransactionType.DEPOSIT);

        Transaction result = TransactionMapper.toTransaction(request, account);

        assertNull(result.getId());
        assertEquals(100.0, result.getAmount().value());
        assertEquals(TransactionType.DEPOSIT, result.getType());
        assertEquals(account, result.getAccount());
    }

    // ─── toResponse ────────────────────────────────────────────────

    @Test
    void toResponse_shouldMapTransactionToResponse() {
        Transaction transaction = new Transaction(1L, new Amount(100.0), TransactionType.DEPOSIT, new Account(1L, new Amount(500.0), "John"));

        TransactionResponse result = TransactionMapper.toResponse(transaction);

        assertEquals(1L, result.transactionId());
        assertEquals(100.0, result.amount());
        assertEquals(TransactionType.DEPOSIT, result.type());
    }
}
package com.tiny.ledger.transaction.dto;

import com.tiny.ledger.transaction.domain.TransactionType;

public record TransactionResponse(Long transactionId, Double amount, TransactionType type) {
}

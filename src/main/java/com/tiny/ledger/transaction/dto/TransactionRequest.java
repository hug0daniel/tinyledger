package com.tiny.ledger.transaction.dto;

import com.tiny.ledger.transaction.domain.TransactionType;

public record TransactionRequest(Double amount,TransactionType transactionType) {
}

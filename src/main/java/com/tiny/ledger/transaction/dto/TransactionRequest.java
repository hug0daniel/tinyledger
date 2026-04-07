package com.tiny.ledger.transaction.dto;

import com.tiny.ledger.transaction.domain.TransactionType;

import java.math.BigDecimal;

public record TransactionRequest(BigDecimal amount, TransactionType transactionType) {
}

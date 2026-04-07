package com.tiny.ledger.transaction.dto;

import com.tiny.ledger.transaction.domain.TransactionType;

import java.math.BigDecimal;

public record TransactionResponse(Long transactionId, BigDecimal amount, TransactionType type) {
}

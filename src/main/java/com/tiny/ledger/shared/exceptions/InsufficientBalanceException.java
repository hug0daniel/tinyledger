package com.tiny.ledger.shared.exceptions;

public class InsufficientBalanceException extends RuntimeException {
    public InsufficientBalanceException() {
        super("Insufficient balance for this withdrawal");
    }
}

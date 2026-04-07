package com.tiny.ledger.shared;

import com.tiny.ledger.shared.exceptions.InsufficientBalanceException;
import com.tiny.ledger.shared.exceptions.InvalidAmountException;

import java.math.BigDecimal;

public record Amount(BigDecimal value) {
    public Amount {
        validateAmount(value);
    }

    public Amount deposit(Amount amount) {
        return new Amount(this.value.add(amount.value));
    }

    public Amount withdraw(Amount amount) {
        if (this.value.subtract(amount.value).compareTo(BigDecimal.ZERO) < 0) {
            throw new InsufficientBalanceException();
        }
        return new Amount(this.value.subtract(amount.value));
    }

    private void validateAmount(BigDecimal amount) {
        if (amount == null)
            throw new InvalidAmountException("Amount cannot be null");
        if (amount.compareTo(BigDecimal.ZERO) < 0)
            throw new InvalidAmountException("Amount must be greater than or equal to 0");
    }
}

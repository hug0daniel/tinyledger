package com.tiny.ledger.shared;

import com.tiny.ledger.shared.exceptions.InsufficientBalanceException;
import com.tiny.ledger.shared.exceptions.InvalidAmountException;

public record Amount(Double value) {
    public Amount {
        validateAmount(value);
    }

    public Amount deposit(Amount amount) {
        return new Amount(this.value + amount.value);
    }

    public Amount withdraw(Amount amount) {
        if (this.value - amount.value < 0)
            throw new InsufficientBalanceException();
        return new Amount(this.value - amount.value);
    }

    private void validateAmount(Double amount) {
        if (amount == null)
            throw new InvalidAmountException("Amount cannot be null");
        if (amount < 0)
            throw new InvalidAmountException("Amount must be greater than or equal to 0");
    }
}

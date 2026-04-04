package com.tiny.ledger.shared;

public class Amount {
    public Double value;


    public Amount(Double value) {
        validateAmount(value);
        this.value = value;
    }

    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
        this.value = value;
    }

    public Amount deposit(Amount amount){
        Double value = this.value + amount.value;
        return new Amount(value);
    }

    public Amount withdraw(Amount amount) {
        if (this.value - amount.value < 0)
            throw new RuntimeException("You don't have enough baalance for withdraw!");

        Double value = this.value - amount.value;

        return new Amount(value);
    }

    private void validateAmount(Double amount){
        if(amount == null) {
            throw new RuntimeException("Amount cannot be null");
        }

        if ((amount < 0)){
            throw new RuntimeException("Amount must be equal or greater than 0");
        }
    }

}

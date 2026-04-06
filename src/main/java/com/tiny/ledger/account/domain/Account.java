package com.tiny.ledger.account.domain;

import com.tiny.ledger.shared.Amount;

public class Account {
    Long id;
    Amount balance;
    String name;

    public Account(Long id, Amount balance, String name) {
        this.id = id;
        this.balance = balance;
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Amount getBalance() {
        return balance;
    }

    public void setBalance(Amount balance) {
        this.balance = balance;
    }

    public String getName() {
        return name;
    }

}

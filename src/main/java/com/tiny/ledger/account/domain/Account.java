package com.tiny.ledger.account.domain;

import com.tiny.ledger.shared.Amount;

public class Account {
    Long id;
    Amount balance;
    String Name;

    public Account(Long id, Amount balance, String name) {
        this.id = id;
        this.balance = balance;
        Name = name;
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
        return Name;
    }

    public void deposit(Amount amount){
        this.balance = this.balance.deposit(amount);
    }

    public void withdraw(Amount amount){
        this.balance = this.balance.withdraw(amount);
    }
}

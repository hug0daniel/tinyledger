package com.tiny.ledger.transaction.domain;
import com.tiny.ledger.account.domain.Account;
import com.tiny.ledger.shared.Amount;

public class Transaction {

    private Long id;
    private Amount amount;
    private final TransactionType type;
    private Account account;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Amount getAmount() {
        return amount;
    }

    public void setAmount(Amount amount) {
        this.amount = amount;
    }

    public TransactionType getType() {
        return type;
    }

    public Transaction(Long id, Amount amount, TransactionType type, Account account) {
        this.id = id;
        this.amount = amount;
        this.type = type;
        this.account = account;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }
}

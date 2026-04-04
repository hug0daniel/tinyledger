package com.tiny.ledger.transaction.domain;
import com.tiny.ledger.account.dto.AccountResponse;

import java.security.Timestamp;

public class Transaction {

    private double amount;
    private TransactionType type;
    private Timestamp timestamp;
    private AccountResponse account;
}

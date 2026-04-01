package com.tiny.ledger.wallet.domain;

import java.security.Timestamp;
import java.util.UUID;

public class Transaction {

    private UUID id;
    private double amount;
    private TransactionType type;
    private Timestamp timestamp;
}

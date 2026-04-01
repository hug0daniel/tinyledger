package com.tiny.ledger.wallet.domain;

import com.tiny.ledger.account.domain.Account;

import java.util.List;
import java.util.UUID;

public class Wallet {

    private UUID id;
    private double balance;
    private Account account;
    private List<Transaction> history;
}

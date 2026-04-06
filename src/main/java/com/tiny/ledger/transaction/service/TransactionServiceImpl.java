package com.tiny.ledger.transaction.service;

import com.tiny.ledger.account.domain.Account;
import com.tiny.ledger.account.repository.AccountRepository;
import com.tiny.ledger.shared.Amount;
import com.tiny.ledger.transaction.domain.Transaction;
import com.tiny.ledger.transaction.dto.TransactionRequest;
import com.tiny.ledger.transaction.dto.TransactionResponse;
import com.tiny.ledger.transaction.repository.TransactionRepository;
import com.tiny.ledger.transaction.utils.TransactionMapper;
import org.springframework.stereotype.Component;
import java.util.List;


@Component
public class TransactionServiceImpl implements TransactionService {

    private final TransactionRepository transactionRepository;
    private final AccountRepository accountRepository;

    public TransactionServiceImpl (TransactionRepository transactionRepository, AccountRepository accountRepository) {
        this.transactionRepository = transactionRepository;
        this.accountRepository = accountRepository;
    }

    @Override
    public TransactionResponse saveTransaction(Long userId,TransactionRequest request) {
        Account account = accountRepository.getAccountById(userId)
                .orElseThrow(() -> new RuntimeException("The provided User Doesn't exist"));

        updateAccountBalance(request, account);

        Transaction transaction = TransactionMapper.toTransaction(request, account);
        transactionRepository.saveTransaction(transaction);

        return TransactionMapper.toResponse(transaction);
    }

    @Override
    public List<TransactionResponse> getAllTransactions(Long userId) {
        List<Transaction> transactionList = transactionRepository.findByAccountId(userId);
        if (transactionList.isEmpty()) {
            throw new IllegalArgumentException("No transactions found for user " + userId);
        }
        return transactionList.stream().map(TransactionMapper::toResponse).toList() ;
    }


    private void updateAccountBalance(TransactionRequest transactionRequest, Account account) {
        Amount transactionAmount = new Amount(transactionRequest.amount());

        Amount newBalance = switch (transactionRequest.transactionType()) {
            case WITHDRAW -> account.getBalance().withdraw(transactionAmount);
            case DEPOSIT -> account.getBalance().deposit(transactionAmount);
        };
        account.setBalance(newBalance);
        accountRepository.saveAccount(account);
    }

}

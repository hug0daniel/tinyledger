package com.tiny.ledger.transaction.service;

import com.tiny.ledger.account.domain.Account;
import com.tiny.ledger.account.repository.AccountRepository;
import com.tiny.ledger.shared.Amount;
import com.tiny.ledger.transaction.domain.Transaction;
import com.tiny.ledger.transaction.domain.TransactionType;
import com.tiny.ledger.transaction.dto.TransactionRequest;
import com.tiny.ledger.transaction.dto.TransactionResponse;
import com.tiny.ledger.transaction.repository.TransactionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TransactionServiceImplTest {

    @Mock
    private TransactionRepository transactionRepository;

    @Mock
    private AccountRepository accountRepository;

    @InjectMocks
    private TransactionServiceImpl transactionService;

    private Account account;

    @BeforeEach
    void setUp() {
        account = new Account(1L, new Amount(500.0), "John");
    }

    // ─── saveTransaction ───────────────────────────────────────────

    @Test
    void saveTransaction_shouldSaveAndReturnResponse_whenDeposit() {
        TransactionRequest request = new TransactionRequest(100.0, TransactionType.DEPOSIT);

        when(accountRepository.getAccountById(1L)).thenReturn(Optional.of(account));

        TransactionResponse response = transactionService.saveTransaction(1L, request);

        assertNotNull(response);
        assertEquals(600.0, account.getBalance().value());
        verify(transactionRepository).saveTransaction(any(Transaction.class));
        verify(accountRepository).saveAccount(account);
    }

    @Test
    void saveTransaction_shouldSaveAndReturnResponse_whenWithdraw() {
        TransactionRequest request = new TransactionRequest(100.0, TransactionType.WITHDRAW);

        when(accountRepository.getAccountById(1L)).thenReturn(Optional.of(account));

        TransactionResponse response = transactionService.saveTransaction(1L, request);

        assertNotNull(response);
        assertEquals(400.0, account.getBalance().value());
        verify(transactionRepository).saveTransaction(any(Transaction.class));
        verify(accountRepository).saveAccount(account);
    }

    @Test
    void saveTransaction_shouldThrowException_whenAccountNotFound() {
        TransactionRequest request = new TransactionRequest(100.0, TransactionType.DEPOSIT);

        when(accountRepository.getAccountById(1L)).thenReturn(Optional.empty());

        RuntimeException ex = assertThrows(RuntimeException.class,
                () -> transactionService.saveTransaction(1L, request));

        assertEquals("The provided User Doesn't exist", ex.getMessage());
        verify(transactionRepository, never()).saveTransaction(any());
    }

    @Test
    void saveTransaction_shouldThrowException_whenInsufficientBalance() {
        TransactionRequest request = new TransactionRequest(1000.0, TransactionType.WITHDRAW);

        when(accountRepository.getAccountById(1L)).thenReturn(Optional.of(account));

        assertThrows(RuntimeException.class,
                () -> transactionService.saveTransaction(1L, request));

        verify(transactionRepository, never()).saveTransaction(any());
    }

    // ─── getAllTransactions ─────────────────────────────────────────

    @Test
    void getAllTransactions_shouldReturnList_whenTransactionsExist() {
        List<Transaction> transactions = List.of(
                new Transaction(1L, new Amount(100.0), TransactionType.DEPOSIT, account),
                new Transaction(2L, new Amount(50.0), TransactionType.WITHDRAW, account)
        );

        when(transactionRepository.findByAccountId(1L)).thenReturn(transactions);

        List<TransactionResponse> result = transactionService.getAllTransactions(1L);

        assertEquals(2, result.size());
        verify(transactionRepository).findByAccountId(1L);
    }

    @Test
    void getAllTransactions_shouldThrowException_whenNoTransactionsFound() {
        when(transactionRepository.findByAccountId(1L)).thenReturn(Collections.emptyList());

        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
                () -> transactionService.getAllTransactions(1L));

        assertEquals("No transactions found for user 1", ex.getMessage());
    }
}
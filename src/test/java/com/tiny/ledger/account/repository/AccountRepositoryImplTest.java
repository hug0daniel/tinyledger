package com.tiny.ledger.account.repository;

import com.tiny.ledger.account.domain.Account;
import com.tiny.ledger.shared.Amount;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class AccountRepositoryImplTest {

    private AccountRepositoryImpl accountRepository;

    @BeforeEach
    void setUp() {
        accountRepository = new AccountRepositoryImpl();
    }

    @Test
    void saveAccount_shouldAssignIdAndPersist_whenIdIsNull() {
        Account account = new Account(null, new Amount(500.0), "John");

        accountRepository.saveAccount(account);

        assertNotNull(account.getId());
        assertEquals(1L, account.getId());
    }

    @Test
    void getAccountById_shouldReturnAccount_whenAccountExists() {
        Account account = new Account(null, new Amount(500.0), "John");
        accountRepository.saveAccount(account);

        Optional<Account> result = accountRepository.getAccountById(account.getId());

        assertTrue(result.isPresent());
        assertEquals("John", result.get().getName());
    }

    @Test
    void getAccountById_shouldReturnEmpty_whenAccountDoesNotExist() {
        Optional<Account> result = accountRepository.getAccountById(99L);

        assertTrue(result.isEmpty());
    }
}
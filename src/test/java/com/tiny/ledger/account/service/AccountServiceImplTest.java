package com.tiny.ledger.account.service;

import com.tiny.ledger.account.domain.Account;
import com.tiny.ledger.account.dto.AccountCreationRequest;
import com.tiny.ledger.account.dto.AccountResponse;
import com.tiny.ledger.account.repository.AccountRepository;
import com.tiny.ledger.shared.Amount;
import com.tiny.ledger.shared.exceptions.AccountNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AccountServiceImplTest {

    @Mock
    private AccountRepository accountRepository;

    @InjectMocks
    private AccountServiceImpl accountService;

    // ─── createAccount ─────────────────────────────────────────────

    @Test
    void createAccount_shouldSaveAndReturnResponse() {
        AccountCreationRequest request = new AccountCreationRequest("John", 500.0);

        AccountResponse response = accountService.createAccount(request);

        assertNotNull(response);
        assertEquals("John", response.name());
        assertEquals(500.0, response.balance());
        verify(accountRepository).saveAccount(any(Account.class));
    }

    @Test
    void createAccount_shouldThrowException_whenAmountIsNegative() {
        AccountCreationRequest request = new AccountCreationRequest("John", -100.0);

        assertThrows(RuntimeException.class,
                () -> accountService.createAccount(request));

        verify(accountRepository, never()).saveAccount(any());
    }

    @Test
    void createAccount_shouldThrowException_whenAmountIsNull() {
        AccountCreationRequest request = new AccountCreationRequest("John", null);

        assertThrows(RuntimeException.class,
                () -> accountService.createAccount(request));

        verify(accountRepository, never()).saveAccount(any());
    }

    // ─── getAccountById ────────────────────────────────────────────

    @Test
    void getAccountById_shouldReturnResponse_whenAccountExists() {
        Account account = new Account(1L, new Amount(500.0), "John");

        when(accountRepository.getAccountById(1L)).thenReturn(Optional.of(account));

        AccountResponse response = accountService.getAccountById(1L);

        assertNotNull(response);
        assertEquals(1L, response.userId());
        assertEquals("John", response.name());
        assertEquals(500.0, response.balance());
    }

    @Test
    void getAccountById_shouldThrowAccountNotFoundException_whenAccountDoesNotExist() {
        when(accountRepository.getAccountById(1L)).thenReturn(Optional.empty());

        AccountNotFoundException ex = assertThrows(AccountNotFoundException.class,
                () -> accountService.getAccountById(1L));

        assertTrue(ex.getMessage().contains("1"));
    }

    @Test
    void getAccountById_shouldThrowRuntimeException_whenUserIdIsNull() {
        RuntimeException ex = assertThrows(RuntimeException.class,
                () -> accountService.getAccountById(null));

        assertEquals("UserId cannot be null!", ex.getMessage());
        verify(accountRepository, never()).getAccountById(any());
    }
}
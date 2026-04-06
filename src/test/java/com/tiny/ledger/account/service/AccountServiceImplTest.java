package com.tiny.ledger.account.service;

import com.tiny.ledger.account.domain.Account;
import com.tiny.ledger.account.dto.AccountCreationRequest;
import com.tiny.ledger.account.dto.AccountResponse;
import com.tiny.ledger.account.repository.AccountRepository;
import com.tiny.ledger.shared.Amount;
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

    @Test
    void createAccount_shouldSaveAccountAndReturnResponse() {
        AccountCreationRequest request = new AccountCreationRequest("John", 1000.0);

        doNothing().when(accountRepository).saveAccount(any(Account.class));

        AccountResponse result = accountService.createAccount(request);

        verify(accountRepository).saveAccount(any(Account.class));
        assertEquals("John", result.name());
    }

    @Test
    void getAccountById_nullId_shouldThrowRuntimeException() {
        RuntimeException exception = assertThrows(RuntimeException.class, () -> accountService.getAccountById(null));

        assertTrue(exception.getMessage().contains("UserId cannot be null"));
    }

    @Test
    void getAccountById_accountNotFound_shouldThrowIllegalArgumentException() {
        Long userId = 123L;
        when(accountRepository.getAccountById(userId)).thenReturn(Optional.empty());

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> accountService.getAccountById(userId));

        assertTrue(exception.getMessage().contains("There is no such user"));
    }

    @Test
    void getAccountById_accountFound_shouldReturnAccountResponse() {
        Long userId = 123L;
        Account account = new Account(userId, new Amount(1000.0), "John");
        when(accountRepository.getAccountById(userId)).thenReturn(Optional.of(account));

        AccountResponse result = accountService.getAccountById(userId);

        verify(accountRepository).getAccountById(userId);
        assertEquals(userId, result.id());
        assertEquals("John", result.name());
        assertEquals(1000L, result.balance());
    }
}
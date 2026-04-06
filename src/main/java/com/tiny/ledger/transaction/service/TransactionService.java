package com.tiny.ledger.transaction.service;

import com.tiny.ledger.transaction.dto.TransactionRequest;
import com.tiny.ledger.transaction.dto.TransactionResponse;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface TransactionService {

    TransactionResponse saveTransaction(Long id, TransactionRequest transactionRequest);
    List<TransactionResponse> getAllTransactions(Long id);
}

package com.tiny.ledger.transaction.utils;

import com.tiny.ledger.account.domain.Account;
import com.tiny.ledger.shared.Amount;
import com.tiny.ledger.transaction.domain.Transaction;
import com.tiny.ledger.transaction.dto.TransactionRequest;
import com.tiny.ledger.transaction.dto.TransactionResponse;
import org.springframework.stereotype.Component;

@Component
public class TransactionMapper {

    private TransactionMapper() {}

    public static Transaction toTransaction(TransactionRequest request, Account account) {
        Amount amount = new Amount(request.amount());
        return new Transaction(null, amount, request.transactionType(), account);
    }

    public static TransactionResponse toResponse(Transaction transaction) {
        return new TransactionResponse(
                transaction.getId(),
                transaction.getAmount().value(),
                transaction.getType()
        );
    }
}

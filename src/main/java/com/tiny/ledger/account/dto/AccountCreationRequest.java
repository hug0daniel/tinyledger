package com.tiny.ledger.account.dto;

public record AccountCreationRequest(
         String name,
         Double balance
) {
}

package com.tiny.ledger.account.dto;

import java.math.BigDecimal;

public record AccountCreationRequest(
         String name,
         BigDecimal balance
) {
}

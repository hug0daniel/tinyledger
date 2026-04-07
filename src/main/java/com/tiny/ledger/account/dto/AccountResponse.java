package com.tiny.ledger.account.dto;

import java.math.BigDecimal;

public record AccountResponse(Long userId, String name, BigDecimal balance) {

}

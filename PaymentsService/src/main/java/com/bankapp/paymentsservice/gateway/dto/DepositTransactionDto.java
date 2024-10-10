package com.bankapp.paymentsservice.gateway.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DepositTransactionDto {

    private String externalUserId;
    private String externalAccountId;
    private BigDecimal amount;
    private OffsetDateTime creationDate;
    private DepositTransactionType depositTransactionType;
}
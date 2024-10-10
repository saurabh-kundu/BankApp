package com.bankapp.paymentsservice.dto;

import com.bankapp.paymentsservice.gateway.dto.DepositTransactionType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PaymentRequestDto {

    private String externalUserId;
    private String externalAccountId;
    private BigDecimal amount;
    private DepositTransactionType depositTransactionType;
    private OffsetDateTime creationDate;
}

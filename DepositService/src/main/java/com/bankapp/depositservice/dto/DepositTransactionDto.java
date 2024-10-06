package com.bankapp.depositservice.dto;

import com.bankapp.depositservice.model.DepositTransactionType;
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
public class DepositTransactionDto {

    private String externalUserId;
    private String externalAccountId;
    private BigDecimal amount;
    private OffsetDateTime creationDate;
    private DepositTransactionType depositTransactionType;

}

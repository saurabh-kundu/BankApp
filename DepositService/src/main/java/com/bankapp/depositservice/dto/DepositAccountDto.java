package com.bankapp.depositservice.dto;

import com.bankapp.depositservice.model.AccountState;
import com.bankapp.depositservice.model.AccountType;
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
public class DepositAccountDto {

    private String externalId;
    private AccountType accountType;
    private BigDecimal interestRate;
    private String externalUserId;
    private AccountState accountState;
    private OffsetDateTime creationDate;
    private OffsetDateTime modifiedDate;
}

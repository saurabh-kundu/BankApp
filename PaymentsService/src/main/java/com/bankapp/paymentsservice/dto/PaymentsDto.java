package com.bankapp.paymentsservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PaymentsDto {

    private Long userId;
    private Long accountId;
    private BigDecimal amount;
    private Date creationDate;
}

package com.bankapp.depositservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DepositAccountBalanceDto {

    private BigDecimal interestRate;
    private BigDecimal minBalance;
    private BigDecimal accountBalance;
    private BigDecimal interestBalance;
    private BigDecimal expectedInterestPerYear;
    private BigDecimal expectedInterestPerMonth;
    private BigDecimal actualInterestPerYear;
    private BigDecimal actualInterestPerMonth;
}

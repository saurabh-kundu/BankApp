package com.bankapp.depositservice.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@Entity
@Table(name = "DepositAccountBalance")
@NoArgsConstructor
@AllArgsConstructor
public class DepositAccountBalance {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;
    @Column(name = "externalAccountId", nullable = false, unique = true)
    private String externalAccountId;
    @Column(name = "interestRate", nullable = false)
    private BigDecimal interestRate;
    @Column(name = "accountBalance", nullable = false)
    private BigDecimal accountBalance;
    @Column(name = "minBalance")
    private BigDecimal minBalance;
    @Column(name = "interestBalance")
    private BigDecimal interestBalance;
    @Column(name = "expectedInterestPerYear")
    private BigDecimal expectedInterestPerYear;
    @Column(name = "expectedInterestPerMonth")
    private BigDecimal expectedInterestPerMonth;
    @Column(name = "actualInterestPerYear")
    private BigDecimal actualInterestPerYear;
    @Column(name = "actualInterestPerMonth")
    private BigDecimal actualInterestPerMonth;
}

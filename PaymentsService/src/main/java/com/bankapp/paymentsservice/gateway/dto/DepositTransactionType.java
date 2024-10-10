package com.bankapp.paymentsservice.gateway.dto;

import lombok.Getter;

@Getter
public enum DepositTransactionType {

    DEPOSIT ("deposit"),
    WITHDRAWAL ("withdrawal");

    private final String depositTransactionType;

    DepositTransactionType(String depositTransactionType) {
        this.depositTransactionType = depositTransactionType;
    }
}

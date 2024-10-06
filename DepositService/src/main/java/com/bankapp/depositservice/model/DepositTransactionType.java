package com.bankapp.depositservice.model;

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

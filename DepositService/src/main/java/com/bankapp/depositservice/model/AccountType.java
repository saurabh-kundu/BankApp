package com.bankapp.depositservice.model;

import lombok.Getter;

@Getter
public enum AccountType {

    SAVINGS("savings"),
    CURRENT("current");

    private final String accountType;

    AccountType(String accountType) {
        this.accountType = accountType;
    }
}

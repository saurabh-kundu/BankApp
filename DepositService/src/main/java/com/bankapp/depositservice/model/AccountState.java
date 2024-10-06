package com.bankapp.depositservice.model;

import lombok.Getter;

@Getter
public enum AccountState {

    PENDING_APPROVAL("pending_approval"),
    APPROVED("approved"),
    ACTIVE("active"),
    INACTIVE("inactive"),
    DORMANT("dormant"),
    CLOSED("closed");

    private final String accountState;

    AccountState(String accountState) {
        this.accountState = accountState;
    }

    }

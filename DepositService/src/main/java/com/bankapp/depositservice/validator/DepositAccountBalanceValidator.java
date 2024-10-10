package com.bankapp.depositservice.validator;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Slf4j
@Component
@RequiredArgsConstructor
public class DepositAccountBalanceValidator {

    public void validateDepositBalance(BigDecimal accountBalance, BigDecimal minBalance) {

        if (accountBalance.compareTo(BigDecimal.ZERO) <= 0) {

            throw new RuntimeException("Insufficient account balance"); // throw dedicated exception here
        }

        if (accountBalance.compareTo(minBalance) < 0) {

            throw new RuntimeException("Account Balance is less than minimum balance"); // throw dedicated exception here
        }

    }

    public void validateUpdatedBalance(BigDecimal updatedBalance) {

        if (updatedBalance.compareTo(BigDecimal.ZERO) < 0) {

            throw new RuntimeException("Cannot perform withdrawal as the account balance will be negative"); // throw dedicated exception here
        }
    }
}

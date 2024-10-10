package com.bankapp.depositservice.validator;

import com.bankapp.depositservice.gateway.UserServiceGateway;
import com.bankapp.depositservice.model.DepositAccountBalance;
import com.bankapp.depositservice.service.DepositBalanceService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Slf4j
@Component
@RequiredArgsConstructor
public class DepositAccountValidator {

    private final UserServiceGateway userServiceGateway;
    private final DepositBalanceService depositBalanceService;

    public void validateMinimumBalance(BigDecimal openingBalance) {

        BigDecimal minimumBalance = depositBalanceService.getMinimumBalance();

        if (openingBalance.compareTo(minimumBalance) < 0) {
            throw new IllegalArgumentException("Opening balance is less than the minimum required balance of: " + minimumBalance);
        }
    }
}

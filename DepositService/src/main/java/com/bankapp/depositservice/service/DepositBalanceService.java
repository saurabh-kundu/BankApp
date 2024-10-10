package com.bankapp.depositservice.service;

import com.bankapp.depositservice.model.DepositAccount;
import com.bankapp.depositservice.model.DepositAccountBalance;
import com.bankapp.depositservice.repository.DepositBalanceRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Slf4j
@Service
@RequiredArgsConstructor
public class DepositBalanceService {

    private final DepositBalanceRepository depositBalanceRepository;

    public void updateBalanceOnAccountCreation(DepositAccount depositAccount, BigDecimal interestRate) {

        BigDecimal openingBalance = depositAccount.getOpeningBalance();

        DepositAccountBalance depositAccountBalance = DepositAccountBalance.builder()
                .accountBalance(depositAccount.getOpeningBalance())
                .minBalance(getMinimumBalance())
                .interestBalance(BigDecimal.ZERO)
                .actualInterestPerYear(BigDecimal.ZERO)
                .actualInterestPerMonth(BigDecimal.ZERO)
                .expectedInterestPerYear(getExpectedInterestPerYear(openingBalance, interestRate))
                .expectedInterestPerMonth(getExpectedInterestPerMonth(openingBalance, interestRate))
                .externalAccountId(depositAccount.getExternalId())
                .build();

        depositBalanceRepository.save(depositAccountBalance);
    }

    /**
     * Invalidates cache if any and then repopulate the cache
     *
     * @param externalDepositAccountId - external id of the deposit account
     * @param amount - transaction amount
     */
    @Transactional
    public void updateBalance(String externalDepositAccountId, BigDecimal amount) {

        DepositAccountBalance depositAccountBalance = depositBalanceRepository
                .findByExternalAccountId(externalDepositAccountId)
                .orElseThrow(
                        () -> new IllegalArgumentException("No deposit account Id with external Id: " + externalDepositAccountId + " found"));

        BigDecimal currentBalance = depositAccountBalance.getAccountBalance();
        BigDecimal updatedBalance = currentBalance.add(amount);
        BigDecimal interestRate = depositAccountBalance.getInterestRate();

        BigDecimal expectedInterestPerYear = getExpectedInterestPerYear(updatedBalance, interestRate);
        BigDecimal expectedInterestPerMonth = getExpectedInterestPerMonth(updatedBalance, interestRate);

        depositAccountBalance.setAccountBalance(updatedBalance);
        depositAccountBalance.setExpectedInterestPerYear(expectedInterestPerYear);
        depositAccountBalance.setExpectedInterestPerMonth(expectedInterestPerMonth);

        depositBalanceRepository.save(depositAccountBalance);
    }

    /**
     * Interest = Principal * Rate of Interest * Time period
     *
     * @param openingBalance      - Principal
     * @param interestRatePerYear - Rate of Interest per year
     * @return - calculated Interest
     */
    private BigDecimal getExpectedInterestPerMonth(BigDecimal openingBalance, BigDecimal interestRatePerYear) {

        BigDecimal interest = openingBalance
                .multiply(interestRatePerYear)
                .divide(new BigDecimal(100), RoundingMode.HALF_DOWN)
                .divide(new BigDecimal(12), RoundingMode.HALF_DOWN);

        interest = interest.setScale(2, RoundingMode.HALF_DOWN);

        return interest;
    }

    /**
     * Interest = Principal * Rate of Interest * Time period
     *
     * @param balance             - Principal balance
     * @param interestRatePerYear - Rate of Interest per year
     * @return - calculated Interest
     */
    private BigDecimal getExpectedInterestPerYear(BigDecimal balance, BigDecimal interestRatePerYear) {

        BigDecimal interest = balance
                .multiply(interestRatePerYear)
                .divide(new BigDecimal(100), RoundingMode.HALF_DOWN);

        interest = interest.setScale(2, RoundingMode.HALF_DOWN);

        return interest;
    }

    public BigDecimal getMinimumBalance() {

        return new BigDecimal("1000");
    }
}

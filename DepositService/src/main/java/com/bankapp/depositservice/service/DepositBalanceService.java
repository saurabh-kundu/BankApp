package com.bankapp.depositservice.service;

import com.bankapp.depositservice.model.DepositAccount;
import com.bankapp.depositservice.model.DepositAccountBalance;
import com.bankapp.depositservice.repository.DepositBalanceRepository;
import com.bankapp.depositservice.validator.DepositAccountBalanceValidator;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class DepositBalanceService {

	private final DepositBalanceRepository depositBalanceRepository;
	private final DepositAccountBalanceValidator accountBalanceValidator;

	/**
	 * Returns the minimum balance as per bank regulations
	 *
	 * @return - Minimum balance of the bank account
	 */
	public BigDecimal getMinimumBalance() {

		return new BigDecimal("1000"); // this will be externalised in some config file
	}

	/**
	 * Get account balance by externalAccountId
	 *
	 * @param externalAccountId - external account Id
	 * @return - account balance
	 */
	public BigDecimal getAccountBalance(String externalAccountId) {

		Optional<BigDecimal> accountBalance = depositBalanceRepository.getAccountBalanceByExternalAccountId(externalAccountId);

		return accountBalance.orElseThrow(() -> new IllegalArgumentException("Balance not found for accountId: " + externalAccountId));
	}


	/**
	 * Updates account balance for new opened accounts on creation
	 *
	 * @param depositAccount - Deposit account
	 * @param interestRate   - interest rate on account
	 */
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
	 * Withdraw deposit account
	 * - performs some validations
	 * - updates balance
	 *
	 * @param externalDepositAccountId - external id of the deposit account
	 * @param transactionAmount        - amount to be withdrawn from the account
	 */
	@Transactional
	public void performWithdrawalByAccountId(String externalDepositAccountId, BigDecimal transactionAmount) {

		DepositAccountBalance depositAccountBalance = getDepositAccountBalance(externalDepositAccountId);

		BigDecimal accountBalance = depositAccountBalance.getAccountBalance();
		BigDecimal minBalance = getMinimumBalance();

		accountBalanceValidator.validateDepositBalance(accountBalance, minBalance);

		BigDecimal currentBalance = depositAccountBalance.getAccountBalance();

		BigDecimal updatedBalance = currentBalance.subtract(transactionAmount);

		accountBalanceValidator.validateUpdatedBalance(updatedBalance);

		updateBalance(depositAccountBalance, updatedBalance);

		depositBalanceRepository.save(depositAccountBalance);
	}

	/**
	 * Deposit deposit account
	 * - performs some validations
	 * - updates balance
	 *
	 * @param externalAccountId - external Id of the deposit account
	 * @param transactionAmount - amount to be deposited to the account
	 */
	public void performDepositByAccountId(String externalAccountId, BigDecimal transactionAmount) {

		DepositAccountBalance depositAccountBalance = getDepositAccountBalance(externalAccountId);

		BigDecimal accountBalance = depositAccountBalance.getAccountBalance();

		BigDecimal updatedBalance = accountBalance.add(transactionAmount);

		updateBalance(depositAccountBalance, updatedBalance);

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

		return interest.setScale(2, RoundingMode.HALF_DOWN);
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

		return interest.setScale(2, RoundingMode.HALF_DOWN);
	}

	private DepositAccountBalance getDepositAccountBalance(String externalDepositAccountId) {

		return depositBalanceRepository
				.findByExternalAccountId(externalDepositAccountId)
				.orElseThrow(
						() -> new IllegalArgumentException("No deposit account Id with external Id: " + externalDepositAccountId + " found"));
	}

	private void updateBalance(DepositAccountBalance depositAccountBalance, BigDecimal updatedBalance) {

		BigDecimal interestRate = depositAccountBalance.getInterestRate();

		BigDecimal expectedInterestPerYear = getExpectedInterestPerYear(updatedBalance, interestRate);
		BigDecimal expectedInterestPerMonth = getExpectedInterestPerMonth(updatedBalance, interestRate);

		depositAccountBalance.setAccountBalance(updatedBalance);
		depositAccountBalance.setExpectedInterestPerYear(expectedInterestPerYear);
		depositAccountBalance.setExpectedInterestPerMonth(expectedInterestPerMonth);
	}
}

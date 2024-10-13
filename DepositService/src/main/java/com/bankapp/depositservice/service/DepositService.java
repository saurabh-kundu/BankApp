package com.bankapp.depositservice.service;

import com.bankapp.depositservice.dto.ChangeStateDto;
import com.bankapp.depositservice.dto.DepositAccountBalanceDto;
import com.bankapp.depositservice.dto.DepositAccountDto;
import com.bankapp.depositservice.mapper.DepositAccountBalanceMapper;
import com.bankapp.depositservice.mapper.DepositAccountMapper;
import com.bankapp.depositservice.model.DepositAccount;
import com.bankapp.depositservice.model.DepositAccountBalance;
import com.bankapp.depositservice.repository.DepositBalanceRepository;
import com.bankapp.depositservice.repository.DepositRepository;
import com.bankapp.depositservice.validator.DepositAccountBalanceValidator;
import com.bankapp.depositservice.validator.UserValidator;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static com.bankapp.depositservice.model.AccountState.PENDING_APPROVAL;

@Slf4j
@Service
@RequiredArgsConstructor
public class DepositService {

	private final DepositRepository depositRepository;
	private final DepositAccountMapper depositAccountMapper;
	private final DepositAccountBalanceValidator depositAccountBalanceValidator;
	private final DepositBalanceRepository depositBalanceRepository;
	private final DepositAccountBalanceMapper depositAccountBalanceMapper;
	private final DepositBalanceService depositBalanceService;
	private final UserValidator userValidator;

	/**
	 * Creates a deposit account for user
	 *
	 * @param depositAccountDto - request to open deposit account
	 */
	@Transactional
	public void createAccount(DepositAccountDto depositAccountDto) {

		userValidator.validateUser(depositAccountDto.getExternalUserId());

		BigDecimal minBalance = depositBalanceService.getMinimumBalance();
		BigDecimal openingBalance = depositAccountDto.getOpeningBalance();

		depositAccountBalanceValidator.validateOpeningMinimumBalance(openingBalance, minBalance);

		BigDecimal interestRate = depositAccountDto.getInterestRate();

		DepositAccount depositAccount = getDepositAccount(depositAccountDto);

		depositRepository.save(depositAccount);

		depositBalanceService.updateBalanceOnAccountCreation(depositAccount, interestRate);
	}

	/**
	 * Updates the state of deposit account
	 *
	 * @param depositAccountId - Id of the deposit account
	 * @param changeStateDto   - State change request
	 */
	@Transactional
	public void updateState(String depositAccountId, ChangeStateDto changeStateDto) {

		DepositAccount depositAccount = getDepositAccount(depositAccountId);

		depositAccount.setAccountState(changeStateDto.getAccountState());

		depositRepository.save(depositAccount);
	}

	/**
	 * @return - List of deposit account details
	 */
	public List<DepositAccountDto> getAllDepositAccounts() {

		List<DepositAccount> depositAccounts = depositRepository.findAll();

		return depositAccounts.stream()
				.map(this::mapDepositAccounts)
				.collect(Collectors.toList());
	}

	/**
	 * @param depositAccountId - Id of the deposit account
	 * @return - Details of the deposit account
	 */
	public DepositAccountDto getAccountById(String depositAccountId) {

		DepositAccount depositAccount = getDepositAccount(depositAccountId);

		return depositAccountMapper.mapModelToDto(depositAccount);
	}

	/**
	 * @param userId - Id of the user
	 * @return - List of deposit account details belonging to the user
	 */
	public List<DepositAccountDto> getDepositAccountsByUserId(String userId) {

		userValidator.validateUser(userId);

		List<DepositAccount> depositAccounts = depositRepository.findAllByExternalUserId(userId);

		return depositAccounts.stream()
				.map(this::mapDepositAccounts)
				.collect(Collectors.toList());
	}

	/**
	 * @param depositAccountId - Id of the deposit account
	 * @return - balance of the deposit account
	 */
	public DepositAccountBalanceDto getBalances(String depositAccountId) {

		DepositAccountBalance depositAccountBalance = depositBalanceRepository.findByExternalAccountId(depositAccountId)
				.orElseThrow(()
						-> new IllegalArgumentException("Deposit account not found by Id: " + depositAccountId));

		return depositAccountBalanceMapper.mapBalanceToDto(depositAccountBalance);
	}

	private DepositAccountDto mapDepositAccounts(DepositAccount depositAccount) {

		return depositAccountMapper.mapModelToDto(depositAccount);
	}

	private DepositAccount getDepositAccount(DepositAccountDto depositAccountDto) {

		DepositAccount depositAccount = depositAccountMapper.mapDtoToModel(depositAccountDto);

		depositAccount.setExternalId(UUID.randomUUID().toString());

		depositAccount.setAccountState(PENDING_APPROVAL);

		return depositAccount;
	}

	private DepositAccount getDepositAccount(String depositAccountId) {

		return depositRepository.findByExternalId(depositAccountId)
				.orElseThrow(() -> new IllegalArgumentException("Invalid deposit account Id: " + depositAccountId));
	}
}

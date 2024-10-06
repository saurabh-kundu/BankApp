package com.bankapp.depositservice.service;

import com.bankapp.depositservice.dto.ChangeStateDto;
import com.bankapp.depositservice.dto.DepositAccountDto;
import com.bankapp.depositservice.mapper.DepositAccountMapper;
import com.bankapp.depositservice.model.AccountState;
import com.bankapp.depositservice.model.DepositAccount;
import com.bankapp.depositservice.repository.DepositRepository;
import com.bankapp.depositservice.validator.DepositAccountValidator;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class DepositService {

    private final DepositRepository depositRepository;
    private final DepositAccountMapper depositAccountMapper;
    private final DepositAccountValidator depositAccountValidator;

    @Transactional
    public void createAccount(DepositAccountDto depositAccountDto) {
        depositAccountValidator.validateUserId(depositAccountDto.getExternalUserId());
        DepositAccount depositAccount = getDepositAccount(depositAccountDto);
        depositRepository.save(depositAccount);
    }

    @Transactional
    public void updateState(String depositAccountId, ChangeStateDto changeStateDto) {
        DepositAccount depositAccount = depositRepository.findByExternalId(depositAccountId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid deposit account Id: " + depositAccountId));
        depositAccount.setAccountState(changeStateDto.getAccountState());
        depositRepository.save(depositAccount);
    }

    public List<DepositAccountDto> getAllDepositAccounts() {
        List<DepositAccount> depositAccounts = depositRepository.findAll();
        return depositAccounts.stream()
                .map(this::mapDepositAccounts)
                .collect(Collectors.toList());
    }

    public DepositAccountDto getAccountById(String depositAccountId) {
        DepositAccount depositAccount = depositRepository
                .findByExternalId(depositAccountId)
                .orElseThrow(() ->
                        new IllegalArgumentException("Deposit account not found by Id: " + depositAccountId));
        return depositAccountMapper.mapModelToDto(depositAccount);
    }

    public List<DepositAccountDto> getDepositAccountsByUserId(String userId) {
        List<DepositAccount> depositAccounts = depositRepository.findAllByExternalUserId(userId);
        return depositAccounts.stream()
                .map(this::mapDepositAccounts)
                .collect(Collectors.toList());
    }

    private DepositAccountDto mapDepositAccounts(DepositAccount depositAccount) {
        return depositAccountMapper.mapModelToDto(depositAccount);
    }

    private DepositAccount getDepositAccount(DepositAccountDto depositAccountDto) {
        DepositAccount depositAccount = depositAccountMapper.mapDtoToModel(depositAccountDto);
        depositAccount.setExternalId(UUID.randomUUID().toString());
        depositAccount.setAccountState(AccountState.PENDING_APPROVAL);

        return depositAccount;
    }
}

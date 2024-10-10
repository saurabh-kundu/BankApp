package com.bankapp.depositservice.service;

import com.bankapp.depositservice.dto.DepositTransactionDto;
import com.bankapp.depositservice.mapper.DepositTransactionMapper;
import com.bankapp.depositservice.model.DepositAccountBalance;
import com.bankapp.depositservice.model.DepositTransaction;
import com.bankapp.depositservice.model.DepositTransactionType;
import com.bankapp.depositservice.repository.TransactionRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class TransactionService {

    private final TransactionRepository transactionRepository;
    private final DepositTransactionMapper depositTransactionMapper;
    private final DepositBalanceService depositBalanceService;

    @Transactional
    public void createTransaction(DepositTransactionDto depositTransactionDto) {

        DepositTransaction transaction = depositTransactionMapper.mapDtoToDomain(depositTransactionDto);

        DepositTransactionType depositTransactionType = transaction.getDepositTransactionType();

        switch (depositTransactionType) {
            case DEPOSIT -> performDepositOperation(transaction);
            case WITHDRAWAL -> performWithdrawalOperation(transaction);
            case null, default -> throw new IllegalArgumentException("Invalid deposit transaction type..");
        }

        transactionRepository.save(transaction);
    }

    private void performWithdrawalOperation(DepositTransaction transaction) {

        depositBalanceService.performWithdrawalByAccountId(transaction.getExternalAccountId(), transaction.getAmount());
    }

    private void performDepositOperation(DepositTransaction transaction) {

        depositBalanceService.performDepositByAccountId(transaction.getExternalAccountId(), transaction.getAmount());
    }

    public List<DepositTransactionDto> getAllTransactions() {

        List<DepositTransaction> depositTransactions = transactionRepository.findAll();

        return depositTransactions.stream()
                .map(this::mapTransactionToDto)
                .collect(Collectors.toList());
    }

    public List<DepositTransactionDto> getTransactionsByAccountId(String externalAccountId) {

        List<DepositTransaction> depositTransactions = transactionRepository.findAllByExternalAccountId(externalAccountId);

        return depositTransactions.stream()
                .map(this::mapTransactionToDto)
                .collect(Collectors.toList());
    }

    public List<DepositTransactionDto> getTransactionsByUserId(String externalUserId) {

        List<DepositTransaction> depositTransactions = transactionRepository.findAllByExternalUserId(externalUserId);

        return depositTransactions.stream()
                .map(this::mapTransactionToDto)
                .collect(Collectors.toList());
    }

    private DepositTransactionDto mapTransactionToDto(DepositTransaction depositTransaction) {

        return depositTransactionMapper.mapDomainToDto(depositTransaction);
    }
}

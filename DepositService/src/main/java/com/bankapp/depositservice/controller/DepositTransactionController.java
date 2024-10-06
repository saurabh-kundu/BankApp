package com.bankapp.depositservice.controller;

import com.bankapp.depositservice.dto.DepositTransactionDto;
import com.bankapp.depositservice.service.TransactionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/api/v1/deposits/transactions")
public class DepositTransactionController {

    private final TransactionService transactionService;

    @GetMapping(produces = "application/json")
    public List<DepositTransactionDto> getAllTransactions() {
        return transactionService.getAllTransactions();
    }

    @GetMapping(path = "/{accountId}", produces = "application/json")
    public List<DepositTransactionDto> getAllTransactionsByAccountId(@PathVariable String accountId) {
        return transactionService.getTransactionsByAccountId(accountId);
    }

    @GetMapping(path = "/{userId}", produces = "application/json")
    public List<DepositTransactionDto> getAllTransactionsByUserId(@PathVariable String externalUserId) {
        return transactionService.getTransactionsByUserId(externalUserId);
    }
}

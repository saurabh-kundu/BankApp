package com.bankapp.depositservice.controller;

import com.bankapp.depositservice.dto.DepositTransactionDto;
import com.bankapp.depositservice.service.TransactionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/api/v1/deposits/transactions")
public class DepositTransactionController {

	private final TransactionService transactionService;

	@PostMapping(consumes = "application/json")
	public ResponseEntity<Void> createTransactions(@RequestBody DepositTransactionDto depositTransactionDto) {

		transactionService.createTransaction(depositTransactionDto);

		return new ResponseEntity<>(HttpStatus.CREATED);
	}

	@GetMapping(produces = "application/json")
	public ResponseEntity<List<DepositTransactionDto>> getAllTransactions() {

		return ResponseEntity.ok(transactionService.getAllTransactions());
	}

	@GetMapping(path = "/{accountId}", produces = "application/json")
	public ResponseEntity<List<DepositTransactionDto>> getAllTransactionsByAccountId(@PathVariable String accountId) {

		return ResponseEntity.ok(transactionService.getTransactionsByAccountId(accountId));
	}

	@GetMapping(path = "/{userId}", produces = "application/json")
	public ResponseEntity<List<DepositTransactionDto>> getAllTransactionsByUserId(@PathVariable String externalUserId) {

		return ResponseEntity.ok(transactionService.getTransactionsByUserId(externalUserId));
	}
}

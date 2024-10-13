package com.bankapp.depositservice.controller;

import com.bankapp.depositservice.dto.ChangeStateDto;
import com.bankapp.depositservice.dto.DepositAccountBalanceDto;
import com.bankapp.depositservice.dto.DepositAccountDto;
import com.bankapp.depositservice.service.DepositService;
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
@RequestMapping(path = "/api/v1/deposits")
public class DepositController {

	private final DepositService depositService;

	@PostMapping(consumes = "application/json")
	public ResponseEntity<Void> createDepositAccount(@RequestBody DepositAccountDto depositAccountDto) {

		depositService.createAccount(depositAccountDto);
		return new ResponseEntity<>(HttpStatus.CREATED);
	}

	@PostMapping(path = "/{depositAccountId}/state", consumes = "application/json")
	public ResponseEntity<Void> updateDepositAccountState(@PathVariable String depositAccountId, @RequestBody ChangeStateDto changeStateDto) {

		depositService.updateState(depositAccountId, changeStateDto);
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@GetMapping(path = "/{depositAccountId}/balances", produces = "application/json")
	public ResponseEntity<DepositAccountBalanceDto> getDepositAccountBalancesById(@PathVariable String depositAccountId) {

		return ResponseEntity.ok(depositService.getBalances(depositAccountId));
	}

	@GetMapping(produces = "application/json")
	public ResponseEntity<List<DepositAccountDto>> getAllDepositAccounts() {

		return ResponseEntity.ok(depositService.getAllDepositAccounts());
	}

	@GetMapping(path = "/{depositAccountId}", produces = "application/json")
	public ResponseEntity<DepositAccountDto> getDepositAccountById(@PathVariable String depositAccountId) {

		return ResponseEntity.ok(depositService.getAccountById(depositAccountId));
	}

	@GetMapping(path = "/userId", produces = "application/json")
	public ResponseEntity<List<DepositAccountDto>> getDepositAccountsByUserId(@PathVariable String userId) {

		return ResponseEntity.ok(depositService.getDepositAccountsByUserId(userId));
	}
}

package com.bankapp.depositservice.mcp;

import com.bankapp.depositservice.dto.DepositAccountDto;
import com.bankapp.depositservice.service.DepositService;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DepositAccountMcpTool {

	private final DepositService depositService;

	@Tool(
			name = "createDepositAccount",
			description = "Creates a deposit account and returns the id"
	)
	public String createAccount(DepositAccountDto depositAccountDto) {

		depositService.createAccount(depositAccountDto);

		return "Deposit account created";
	}

}

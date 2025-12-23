package com.bankapp.depositservice;

import com.bankapp.depositservice.mcp.DepositAccountMcpTool;
import com.bankapp.depositservice.mcp.MCPServerHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.support.ToolCallbacks;
import org.springframework.ai.tool.ToolCallback;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.List;

@Slf4j
@SpringBootApplication
public class DepositServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(DepositServiceApplication.class, args);
	}

	@Bean
	public List<ToolCallback> findTools(DepositAccountMcpTool depositAccountMcpTool) {
		return List.of(ToolCallbacks.from(depositAccountMcpTool));
	}

	@Bean
	CommandLineRunner mcpServerRunner(MCPServerHandler mcpHandler) {
		return args -> {
			mcpHandler.start();
		};
	}
}
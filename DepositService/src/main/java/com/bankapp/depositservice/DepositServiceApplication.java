package com.bankapp.depositservice;

import com.bankapp.depositservice.mcpserver.handler.MCPServerHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@Slf4j
@SpringBootApplication
public class DepositServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(DepositServiceApplication.class, args);
	}

	@Bean
	CommandLineRunner mcpServerRunner(MCPServerHandler mcpHandler) {
		return args -> mcpHandler.start();
	}
}
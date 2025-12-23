package com.bankapp.depositservice.mcpserver.handler;

import com.bankapp.depositservice.dto.DepositAccountDto;
import com.bankapp.depositservice.model.AccountType;
import com.bankapp.depositservice.service.DepositService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.util.Map;

@Slf4j
@Component
public class MCPServerHandler {

	private final ObjectMapper objectMapper;
	private final DepositService depositService;

	private static final String PROTOCOL_VERSION = "2025-06-18";
	private static final String SERVER_NAME = "depositService";
	private static final String SERVER_VERSION = "1.0.0";

	public MCPServerHandler(ObjectMapper objectMapper, DepositService depositService) {
		this.objectMapper = objectMapper;
		this.depositService = depositService;
	}

	public void start() {
		try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
			 PrintWriter writer = new PrintWriter(System.out, true)) {

			/*// Log to stderr (so it doesn't interfere with stdout)
			System.err.println("MCP Server started and listening on stdin...");*/

			log.info("MCP Server started and listening on stdin...");

			String line;
			while ((line = reader.readLine()) != null) {
				handleMessage(line, writer);
			}
		} catch (Exception e) {
			log.error("MCP Server error: " + e.getMessage());
			e.printStackTrace(System.err);
		}
	}

	private void handleMessage(String message, PrintWriter writer) {
		try {
			log.info("Received message: " + message);

			Map<String, Object> request = objectMapper.readValue(message, Map.class);
			String method = (String) request.get("method");
			Object id = request.get("id");

			if (method == null) {
				sendError(writer, null, -32600, "Invalid request: missing method");
				return;
			}

			switch (method) {
				case "initialize":
					handleInitialize(request, writer, id);
					break;
				case "tools/list":
					handleToolsList(writer, id);
					break;
				case "tools/call":
					handleToolsCall(request, writer, id);
					break;
				default:
					sendError(writer, id, -32601, "Method not found: " + method);
			}
		} catch (Exception e) {
			System.err.println("Error handling message: " + e.getMessage());
			e.printStackTrace(System.err);
			sendError(writer, null, -32700, "Parse error: " + e.getMessage());
		}
	}

	private void handleInitialize(Map<String, Object> request, PrintWriter writer, Object id) {
		try {
			Map<String, Object> params = (Map<String, Object>) request.get("params");
			String protocolVersion = (String) params.get("protocolVersion");

			if (!PROTOCOL_VERSION.equals(protocolVersion)) {
				sendError(writer, id, -32602,
						"Unsupported protocol version. Expected: " + PROTOCOL_VERSION);
				return;
			}

			Map<String, Object> response = Map.of(
					"jsonrpc", "2.0",
					"id", id,
					"result", Map.of(
							"protocolVersion", PROTOCOL_VERSION,
							"capabilities", Map.of(
									"tools", Map.of()
							),
							"serverInfo", Map.of(
									"name", SERVER_NAME,
									"version", SERVER_VERSION
							)
					)
			);

			sendResponse(writer, response);
			System.err.println("Initialize successful");

		} catch (Exception e) {
			System.err.println("Initialize error: " + e.getMessage());
			sendError(writer, id, -32603, "Internal error: " + e.getMessage());
		}
	}

	private void handleToolsList(PrintWriter writer, Object id) {
		try {
			Map<String, Object> response = Map.of(
					"jsonrpc", "2.0",
					"id", id,
					"result", Map.of(
							"tools", new Object[]{
									Map.of(
											"name", "create_deposit",
											"description", "Create a new deposit for a customer",
											"inputSchema", Map.of(
													"type", "object",
													"properties", Map.of(
															"openingBalance", Map.of(
																	"type", "number",
																	"description", "Opening balance of the account"
															),
															"interestRate", Map.of(
																	"type", "number",
																	"description", "Interest rate of the account"
															),
															"externalUserId", Map.of(
																	"type", "String",
																	"description", "Id of the user"
															)
													),
													"required", new String[]{ "openingBalance", "interestRate" }
											)
									),
									Map.of(
											"name", "get_deposit",
											"description", "Get deposit details by ID",
											"inputSchema", Map.of(
													"type", "object",
													"properties", Map.of(
															"depositId", Map.of(
																	"type", "string",
																	"description", "Deposit ID"
															)
													),
													"required", new String[]{ "depositId" }
											)
									),
									Map.of(
											"name", "list_deposits",
											"description", "List all deposits for a customer",
											"inputSchema", Map.of(
													"type", "object",
													"properties", Map.of(
															"customerId", Map.of(
																	"type", "string",
																	"description", "Customer ID"
															)
													),
													"required", new String[]{ "customerId" }
											)
									)
							}
					)
			);

			sendResponse(writer, response);
		} catch (Exception e) {
			sendError(writer, id, -32603, "Internal error: " + e.getMessage());
		}
	}

	private void handleToolsCall(Map<String, Object> request, PrintWriter writer, Object id) {
		try {
			Map<String, Object> params = (Map<String, Object>) request.get("params");
			String toolName = (String) params.get("name");
			Map<String, Object> arguments = (Map<String, Object>) params.get("arguments");

			System.err.println("Calling tool: " + toolName + " with args: " + arguments);

			String result = executeToolLogic(toolName, arguments);

			Map<String, Object> response = Map.of(
					"jsonrpc", "2.0",
					"id", id,
					"result", Map.of(
							"content", new Object[]{
									Map.of(
											"type", "text",
											"text", result
									)
							}
					)
			);

			sendResponse(writer, response);
		} catch (Exception e) {
			System.err.println("Tool execution error: " + e.getMessage());
			sendError(writer, id, -32603, "Tool execution error: " + e.getMessage());
		}
	}

	private String executeToolLogic(String toolName, Map<String, Object> arguments) {
		// Call your actual DepositService methods here
		switch (toolName) {
			case "create_deposit":
				DepositAccountDto depositAccountDto = new DepositAccountDto();
				depositAccountDto.setAccountType(AccountType.SAVINGS);
				depositAccountDto.setExternalUserId((String) arguments.get("externalUserId"));
				depositAccountDto.setOpeningBalance(BigDecimal.valueOf(((Number) arguments.get("openingBalance")).doubleValue()));
				depositAccountDto.setInterestRate(BigDecimal.valueOf(((Number) arguments.get("interestRate")).doubleValue()));
				return depositService.createAccountMCP(depositAccountDto);
			default:
				throw new IllegalArgumentException("Unknown tool: " + toolName);
		}
	}

	private void sendResponse(PrintWriter writer, Map<String, Object> response) {
		try {
			String json = objectMapper.writeValueAsString(response);
			writer.println(json);
			writer.flush();
			System.err.println("Sent response: " + json);
		} catch (Exception e) {
			System.err.println("Error sending response: " + e.getMessage());
		}
	}

	private void sendError(PrintWriter writer, Object id, int code, String message) {
		try {
			Map<String, Object> error = Map.of(
					"jsonrpc", "2.0",
					"id", id != null ? id : "",
					"error", Map.of(
							"code", code,
							"message", message
					)
			);
			String json = objectMapper.writeValueAsString(error);
			writer.println(json);
			writer.flush();
			System.err.println("Sent error: " + json);
		} catch (Exception e) {
			System.err.println("Error sending error response: " + e.getMessage());
		}
	}
}

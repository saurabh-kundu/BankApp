package com.bankapp.paymentsservice.gateway;

import com.bankapp.paymentsservice.gateway.dto.DepositTransactionDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

@Slf4j
@Service
@RequiredArgsConstructor
public class TransactionGateway {

    private static final String DEPOSIT_SERVICE_HOST = "http://localhost:8081/api/v1/deposits";

    public void createTransaction(DepositTransactionDto transactionRequest) {

        RestClient restClient = RestClient.builder()
                .baseUrl(DEPOSIT_SERVICE_HOST)
                .build();

        ResponseEntity<Void> response = restClient.post()
                .uri("/transactions")
                .contentType(MediaType.APPLICATION_JSON)
                .body(transactionRequest)
                .retrieve()
                .toBodilessEntity();

        if (response.getStatusCode() != HttpStatus.CREATED) {

            throw new RuntimeException("Something went wrong while creating transactions, please retry");
        }
    }
}

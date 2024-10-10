package com.bankapp.paymentsservice.controller;

import com.bankapp.paymentsservice.dto.PaymentRequestDto;
import com.bankapp.paymentsservice.service.PaymentsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/api/v1/payments")
public class PaymentsController {

    private final PaymentsService paymentsService;

    //sync payment API - call create Transaction endpoint of deposit service
    @RequestMapping(consumes = "application/json")
    public ResponseEntity<Void> makePayments(@RequestBody PaymentRequestDto paymentRequestDto) {

        paymentsService.makePayments(paymentRequestDto);

        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    //async payment API - uses kafka for pub/sub
}

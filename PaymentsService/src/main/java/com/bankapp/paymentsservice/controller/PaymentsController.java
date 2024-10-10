package com.bankapp.paymentsservice.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/api/v1/payments")
public class PaymentsController {

    //sync payment API - call create Transaction endpoint of deposit service

    //async payment API - uses kafka for pub/sub
}

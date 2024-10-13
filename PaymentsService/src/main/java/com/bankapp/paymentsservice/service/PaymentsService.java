package com.bankapp.paymentsservice.service;

import com.bankapp.paymentsservice.dto.PaymentRequestDto;
import com.bankapp.paymentsservice.gateway.TransactionGateway;
import com.bankapp.paymentsservice.gateway.UserServiceGateway;
import com.bankapp.paymentsservice.gateway.dto.DepositTransactionDto;
import com.bankapp.paymentsservice.gateway.dto.UserDto;
import com.bankapp.paymentsservice.mapper.PaymentsMapper;
import com.bankapp.paymentsservice.model.PaymentDetails;
import com.bankapp.paymentsservice.repository.PaymentsRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class PaymentsService {

	private final PaymentsMapper paymentsMapper;
	private final TransactionGateway transactionGateway;
	private final UserServiceGateway userServiceGateway;
	private final PaymentsRepository paymentsRepository;

	@Transactional
	public void makePayments(PaymentRequestDto paymentRequestDto) {

		// validate user
		Optional<UserDto> userDto = userServiceGateway.getUserByExternalUserId(paymentRequestDto.getExternalUserId());

		userDto
				.orElseThrow(()
						-> new IllegalArgumentException("No user found with userId: " + paymentRequestDto.getExternalUserId()));

		PaymentDetails paymentDetails = paymentsMapper.mapPaymentRequestDtoToDomain(paymentRequestDto);

		paymentsRepository.save(paymentDetails);

		DepositTransactionDto transactionRequest = buildTransactionRequest(paymentRequestDto);

		transactionGateway.createTransaction(transactionRequest);

		log.info("Payment successful");
	}

	private DepositTransactionDto buildTransactionRequest(PaymentRequestDto paymentRequestDto) {

		return DepositTransactionDto.builder()
				.externalUserId(paymentRequestDto.getExternalUserId())
				.externalAccountId(paymentRequestDto.getExternalAccountId())
				.amount(paymentRequestDto.getAmount())
				.depositTransactionType(paymentRequestDto.getDepositTransactionType())
				.build();
	}
}

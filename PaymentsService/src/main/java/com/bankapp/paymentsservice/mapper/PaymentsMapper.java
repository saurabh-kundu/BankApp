package com.bankapp.paymentsservice.mapper;

import com.bankapp.paymentsservice.dto.PaymentRequestDto;
import com.bankapp.paymentsservice.model.PaymentDetails;
import org.mapstruct.Mapper;

import static org.mapstruct.ReportingPolicy.IGNORE;

@Mapper(unmappedTargetPolicy = IGNORE, componentModel = "spring")
public interface PaymentsMapper {

    PaymentDetails mapPaymentRequestDtoToDomain(PaymentRequestDto paymentRequestDto);
}

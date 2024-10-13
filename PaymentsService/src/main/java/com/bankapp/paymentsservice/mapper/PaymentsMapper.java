package com.bankapp.paymentsservice.mapper;

import com.bankapp.paymentsservice.dto.PaymentRequestDto;
import com.bankapp.paymentsservice.model.PaymentDetails;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import static org.mapstruct.ReportingPolicy.IGNORE;

@Mapper(unmappedTargetPolicy = IGNORE, componentModel = "spring")
public interface PaymentsMapper {

    @Mapping(source = "externalUserId", target = "userId")
    @Mapping(source = "externalAccountId", target = "accountId")
    @Mapping(source = "amount", target = "amount")
    @Mapping(source = "depositTransactionType", target = "depositTransactionType")
    PaymentDetails mapPaymentRequestDtoToDomain(PaymentRequestDto paymentRequestDto);
}

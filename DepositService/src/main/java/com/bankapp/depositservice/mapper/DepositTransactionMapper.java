package com.bankapp.depositservice.mapper;

import com.bankapp.depositservice.dto.DepositTransactionDto;
import com.bankapp.depositservice.model.DepositTransaction;
import org.mapstruct.Mapper;

import static org.mapstruct.ReportingPolicy.IGNORE;

@Mapper(unmappedTargetPolicy = IGNORE, componentModel = "spring")
public interface DepositTransactionMapper {

    DepositTransactionDto mapDomainToDto(DepositTransaction depositTransaction);

    DepositTransaction mapDtoToDomain(DepositTransactionDto depositTransactionDto);
}

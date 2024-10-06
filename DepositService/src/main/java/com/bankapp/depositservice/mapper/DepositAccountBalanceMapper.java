package com.bankapp.depositservice.mapper;

import com.bankapp.depositservice.dto.DepositAccountBalanceDto;
import com.bankapp.depositservice.model.DepositAccountBalance;
import org.mapstruct.Mapper;

import static org.mapstruct.ReportingPolicy.IGNORE;

@Mapper(unmappedTargetPolicy = IGNORE, componentModel = "spring")
public interface DepositAccountBalanceMapper {

    DepositAccountBalanceDto mapBalanceToDto(DepositAccountBalance depositAccountBalance);
}

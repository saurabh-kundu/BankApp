package com.bankapp.depositservice.mapper;

import com.bankapp.depositservice.dto.DepositAccountDto;
import com.bankapp.depositservice.model.DepositAccount;
import org.mapstruct.Mapper;

import static org.mapstruct.ReportingPolicy.IGNORE;

@Mapper(unmappedTargetPolicy = IGNORE, componentModel = "spring")
public interface DepositAccountMapper {

    DepositAccount mapDtoToModel(DepositAccountDto depositAccountDto);

    DepositAccountDto mapModelToDto(DepositAccount depositAccount);
}

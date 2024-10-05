package com.bankapp.userservice.mapper;

import com.bankapp.userservice.dto.UserDto;
import com.bankapp.userservice.model.BankUser;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import static org.mapstruct.ReportingPolicy.IGNORE;

@Mapper(unmappedTargetPolicy = IGNORE, componentModel = "spring")
public interface UserMapper {

    @Mapping(source = "firstName", target = "firstName")
    @Mapping(source = "lastName", target = "lastName")
    @Mapping(source = "age", target = "age")
    @Mapping(source = "phoneNumber", target = "phoneNumber")
    @Mapping(source = "address", target = "address")
    @Mapping(source = "externalId", target = "userId")
    @Mapping(source = "dateOfBirth", target = "dateOfBirth")
    @Mapping(source = "email", target = "email")
    UserDto mapBankUserToDto(BankUser bankUser);

    @Mapping(source = "firstName", target = "firstName")
    @Mapping(source = "lastName", target = "lastName")
    @Mapping(source = "age", target = "age")
    @Mapping(source = "phoneNumber", target = "phoneNumber")
    @Mapping(source = "address", target = "address")
    @Mapping(source = "dateOfBirth", target = "dateOfBirth")
    @Mapping(source = "email", target = "email")
    BankUser mapUserDtoToBankUser(UserDto userRequest);

    BankUser mapUpdateBankUser(UserDto userRequest, @MappingTarget BankUser existingBankUser);
}

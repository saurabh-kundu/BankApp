package com.bankapp.depositservice.validator;

import com.bankapp.depositservice.gateway.UserDto;
import com.bankapp.depositservice.gateway.UserServiceGateway;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class DepositAccountValidator {

    private final UserServiceGateway userServiceGateway;

    public void validateUserId(String externalUserId) {
        userServiceGateway.getUserByExternalUserId(externalUserId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid user Id"));
    }
}

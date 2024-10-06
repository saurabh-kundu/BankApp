package com.bankapp.depositservice.gateway;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceGateway {

    private static final String USER_SERVICE_HOST = "http://localhost:8081/api/v1/users";

    public Optional<UserDto> getUserByExternalUserId(String externalUserId) {
        RestClient restClient = RestClient.builder()
                .baseUrl(USER_SERVICE_HOST)
                .build();
        UserDto userDto = restClient.get()
                .uri("/{userId}", externalUserId)
                .retrieve()
                .body(UserDto.class);

        return Optional.ofNullable(userDto);
    }
}

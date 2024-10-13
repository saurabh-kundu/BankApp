package com.bankapp.depositservice.validator;

import com.bankapp.depositservice.gateway.UserServiceGateway;
import com.bankapp.depositservice.gateway.dto.UserDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.lettuce.core.RedisClient;
import io.lettuce.core.api.StatefulRedisConnection;
import io.lettuce.core.api.sync.RedisCommands;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserValidator {

	private final RedisClient redisClient = RedisClient.create("redis://localhost:6379");
	private final UserServiceGateway userServiceGateway;

	private static final ObjectMapper objectMapper = new ObjectMapper();

	public void validateUser(String externalUserId) {

		StatefulRedisConnection<String, String> connection = redisClient.connect();
		RedisCommands<String, String> syncCommands = connection.sync();

		String userString = syncCommands.get(externalUserId);

		if (userString == null) {

			UserDto user = validateAndGetUserByCallingUserService(externalUserId);

			try {

				userString = objectMapper.writeValueAsString(user);

			} catch (JsonProcessingException e) {

				log.error("Cannot create string for user object", e);

				throw new RuntimeException(e);
			}

			syncCommands.set(user.getUserId(), userString);

		} else {

			try {

				UserDto user = objectMapper.readValue(userString, UserDto.class);

				if (user == null) {

					throw new IllegalArgumentException("Invalid user Id: " + externalUserId);
				}

				log.info("Validated user from cache...");

			} catch (JsonProcessingException e) {

				log.error("Cannot read user string to create instance", e);

				throw new RuntimeException(e);
			}
		}
	}

	private UserDto validateAndGetUserByCallingUserService(String externalUserId) {

		log.warn("user not found in cache..making an API call to user service to retrieve details for userId: {}", externalUserId);

		return userServiceGateway.getUserByExternalUserId(externalUserId)
				.orElseThrow(() -> new IllegalArgumentException("Invalid user Id"));
	}
}

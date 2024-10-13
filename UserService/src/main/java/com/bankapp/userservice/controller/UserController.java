package com.bankapp.userservice.controller;

import com.bankapp.userservice.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import com.bankapp.userservice.dto.UserDto;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/api/v1/users")
public class UserController {

	private final UserService userService;

	@GetMapping(produces = "application/json")
	public ResponseEntity<List<UserDto>> getAllUsers() {

		log.trace("get all users api invoked...");

		return ResponseEntity.ok(userService.getAllUsers());
	}

	@GetMapping(path = "/{userId}", produces = "application/json")
	public ResponseEntity<UserDto> getUserById(@PathVariable String userId) {

		log.trace("get user by id: {} api invoked...", userId);

		return ResponseEntity.ok(userService.getUserById(userId));
	}

	@PostMapping(consumes = "application/json")
	public ResponseEntity<Void> addUser(@RequestBody UserDto userRequest) {

		log.trace("adding user api invoked...");

		userService.addUsers(userRequest);

		return new ResponseEntity<>(HttpStatus.CREATED);
	}

	@PatchMapping(consumes = "application/json")
	public ResponseEntity<Void> updateUser(@RequestBody UserDto userRequest) {

		log.trace("updating user by id: {} api invoked...", userRequest.getUserId());

		userService.updateUser(userRequest);

		return new ResponseEntity<>(HttpStatus.OK);
	}

}

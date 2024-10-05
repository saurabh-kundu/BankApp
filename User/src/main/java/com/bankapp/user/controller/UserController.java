package com.bankapp.user.controller;

import com.bankapp.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import com.bankapp.user.dto.UserDto;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/api/v1/users")
public class UserController {

    private final UserService userService;

    @GetMapping(produces = "application/json")
    public List<UserDto> getAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping(path = "/{userId}", produces = "application/json")
    public UserDto getUserById(@PathVariable String userId) {
        return userService.getUserById(userId);
    }

    @PostMapping(consumes = "application/json")
    public void addUser(@RequestBody UserDto userRequest) {
        userService.addUsers(userRequest);
    }

    @PatchMapping(consumes = "application/json")
    public void updateUser(@RequestBody UserDto userRequest) {
        userService.updateUser(userRequest);
    }

}

package com.bankapp.userservice.service;

import com.bankapp.userservice.exception.UserServiceException.NotFoundException;
import com.bankapp.userservice.mapper.UserMapper;
import com.bankapp.userservice.model.BankUser;
import com.bankapp.userservice.repo.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import com.bankapp.userservice.dto.UserDto;

import static com.bankapp.userservice.util.UserConstant.USER_NOT_FOUND;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {

    private final UserMapper userMapper;
    private final UserRepository userRepository;

    public List<UserDto> getAllUsers() {
        List<BankUser> bankUsers = userRepository.findAll();
        log.debug("found total: {} users found...", bankUsers.size());
        return bankUsers.stream()
                .map(this::mapUser)
                .collect(Collectors.toList());
    }

    public UserDto getUserById(String userId) {
        BankUser bankUser = validateAndGetBankUserFromDb(userId);
        return userMapper.mapBankUserToDto(bankUser);
    }

    public void addUsers(UserDto userRequest) {
        BankUser bankUser = userMapper.mapUserDtoToBankUser(userRequest);
        bankUser.setExternalId(UUID.randomUUID().toString());
        userRepository.save(bankUser);
    }

    public void updateUser(UserDto userRequest) {
        BankUser existingBankUser = validateAndGetBankUserFromDb(userRequest.getUserId());
        BankUser updatedUser = userMapper.mapUpdateBankUser(userRequest, existingBankUser);
        userRepository.save(updatedUser);
    }

    private BankUser validateAndGetBankUserFromDb(String userId) {
        return userRepository.findBankUserById(userId)
                .orElseThrow(() -> new NotFoundException(USER_NOT_FOUND + userId));
    }

    private UserDto mapUser(BankUser bankUser) {
        return userMapper.mapBankUserToDto(bankUser);
    }
}

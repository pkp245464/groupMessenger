package com.group.Messenger.features.users.service;

import com.group.Messenger.features.users.dto.UserDetailsDto;
import com.group.Messenger.features.users.dto.UsersDto;

import java.util.Optional;

public interface UsersService {

    UserDetailsDto createUser(UserDetailsDto userDetailsDto);
    String loginUser(String username, String password);
    Optional<UsersDto> getUsersById(Long userId);
    Optional<UsersDto> getUsersByUsername(String username);
    Optional<UserDetailsDto> getUsersByEmailId(String email);
    UsersDto updateUser(UsersDto userDto);
    void deleteUsersByUserName(String userName);
    void deleteUsersByUserId(Long userId);
}

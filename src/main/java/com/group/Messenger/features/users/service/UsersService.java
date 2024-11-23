package com.group.Messenger.features.users.service;

import com.group.Messenger.features.users.dto.UsersDto;
import com.group.Messenger.features.users.models.Users;

import java.util.Optional;

public interface UsersService {

    UsersDto createUser(UsersDto usersDto);
    Optional<UsersDto> getUsersById(Long userId);
    Optional<UsersDto> getUsersByUsername(String username);
    UsersDto updateUser(Long userId, UsersDto userDto);
    void deleteUsersByUserName(String userName);
    void deleteUsersByUserId(Long userId);
}

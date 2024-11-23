package com.group.Messenger.features.users.service;

import com.group.Messenger.core.exceptions.CustomGroupMessengerException;
import com.group.Messenger.features.users.dto.UsersDto;
import com.group.Messenger.features.users.models.Users;
import com.group.Messenger.features.users.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Consumer;

@Service
public class UsersServiceImpl implements UsersService{

    @Autowired
    private UsersRepository usersRepository;

    @Override
    public UsersDto createUser(UsersDto usersDto) {
        Optional<Users> existingUser = usersRepository.findByUserName(usersDto.getUserName());
        if (existingUser.isPresent()) {
            throw new CustomGroupMessengerException("Username already exists");
        }
        Users user = new Users();
        user.setUserName(usersDto.getUserName());
        user.setFirstName(usersDto.getFirstName());
        user.setSecondName(usersDto.getSecondName());
        user.setDateOfBirth(usersDto.getDateOfBirth());
        user.setAddress(usersDto.getAddress());

        Users savedUser = usersRepository.save(user);

        return convertToDto(savedUser);
    }

    @Override
    public Optional<UsersDto> getUsersById(Long userId) {
        Optional<Users>user = usersRepository.findByUserIdAndIsDeletedFalse(userId);
        if (user.isEmpty()) {
            throw new CustomGroupMessengerException("User does not exist by userId: " + userId);
        }
        return user.map(this::convertToDto);
    }

    @Override
    public Optional<UsersDto> getUsersByUsername(String username) {
        Optional<Users> user = usersRepository.findByUserNameAndIsDeletedFalse(username);
        if (user.isEmpty()) {
            throw new CustomGroupMessengerException("User does not exist by username: " + username);
        }
        return user.map(this::convertToDto);
    }

    @Override
    public UsersDto updateUser(UsersDto userDto) {
        Long userId = userDto.getUserId();
        Users existingUser = usersRepository.findById(userId)
                .orElseThrow(() -> new CustomGroupMessengerException("User does not exist by userId: " + userId));

        updateFieldIfNotNull(existingUser::setUserName, userDto.getUserName());
        updateFieldIfNotNull(existingUser::setFirstName, userDto.getFirstName());
        updateFieldIfNotNull(existingUser::setSecondName, userDto.getSecondName());
        updateFieldIfNotNull(existingUser::setDateOfBirth, userDto.getDateOfBirth());
        updateFieldIfNotNull(existingUser::setAddress, userDto.getAddress());

        Users updatedUser = usersRepository.save(existingUser);
        return convertToDto(updatedUser);
    }

    private <T> void updateFieldIfNotNull(Consumer<T> setter, T value) {
        if (value != null) {
            setter.accept(value);
        }
    }

    @Override
    public void deleteUsersByUserName(String userName) {
        Users user = usersRepository.findByUserNameAndIsDeletedFalse(userName)
                .orElseThrow(()->new CustomGroupMessengerException("User not found with userName: " + userName));

        if(Boolean.TRUE.equals(user.getIsDeleted())) {
            throw new CustomGroupMessengerException("User is already deleted with userName: " + userName);
        }
        user.setIsDeleted(true);
        usersRepository.save(user);
    }

    @Override
    public void deleteUsersByUserId(Long userId) {
        Users user = usersRepository.findByUserIdAndIsDeletedFalse(userId)
                .orElseThrow(()->new CustomGroupMessengerException("User not found with userId: " + userId));

        if(Boolean.TRUE.equals(user.getIsDeleted())) {
            throw new CustomGroupMessengerException("User is already deleted with userId: " + userId);
        }
        user.setIsDeleted(true);
        usersRepository.save(user);
    }

    private UsersDto convertToDto(Users user) {
        UsersDto usersDto = new UsersDto();
        usersDto.setUserId(user.getUserId());
        usersDto.setUserName(user.getUserName());
        usersDto.setFirstName(user.getFirstName());
        usersDto.setSecondName(user.getSecondName());
        usersDto.setDateOfBirth(user.getDateOfBirth());
        usersDto.setAddress(user.getAddress());
        usersDto.setIsDeleted(user.getIsDeleted());
        return usersDto;
    }
}

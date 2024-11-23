package com.group.Messenger.features.users.service;

import com.group.Messenger.core.exceptions.CustomGroupMessengerException;
import com.group.Messenger.features.users.dto.UsersDto;
import com.group.Messenger.features.users.models.Users;
import com.group.Messenger.features.users.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

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
        Optional<Users>user = usersRepository.findById(userId);
        return user.map(this::convertToDto);
    }

    @Override
    public Optional<UsersDto> getUsersByUsername(String username) {
        Optional<Users> user = usersRepository.findByUserName(username);
        return user.map(this::convertToDto);
    }

    @Override
    public UsersDto updateUser(Long userId, UsersDto userDto) {
        Optional<Users> existingUserOptional = usersRepository.findById(userId);
        if (existingUserOptional.isPresent()) {
            Users existingUser = existingUserOptional.get();
            existingUser.setUserName(userDto.getUserName());
            existingUser.setFirstName(userDto.getFirstName());
            existingUser.setSecondName(userDto.getSecondName());
            existingUser.setDateOfBirth(userDto.getDateOfBirth());
            existingUser.setAddress(userDto.getAddress());
            existingUser.setUpdatedAt(LocalDateTime.now());

            Users updatedUser = usersRepository.save(existingUser);

            return convertToDto(updatedUser);
        }
        else {
            throw new CustomGroupMessengerException("User does not exist by username: " + userId);
        }
    }

    @Override
    public void deleteUsersByUserName(String userName) {
        Users user = usersRepository.findByUserName(userName)
                .orElseThrow(()->new CustomGroupMessengerException("User not found with userName: " + userName));

        if(Boolean.TRUE.equals(user.getIsDeleted())) {
            throw new CustomGroupMessengerException("User is already deleted with userName: " + userName);
        }
        user.setIsDeleted(true);
        usersRepository.save(user);
    }

    @Override
    public void deleteUsersByUserId(Long userId) {
        Users user = usersRepository.findById(userId)
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

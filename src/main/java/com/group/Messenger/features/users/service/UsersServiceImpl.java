package com.group.Messenger.features.users.service;

import com.group.Messenger.core.enums.LoginProviders;
import com.group.Messenger.core.exceptions.CustomGroupMessengerException;
import com.group.Messenger.core.jwt.JwtHelper;
import com.group.Messenger.features.users.dto.UserDetailsDto;
import com.group.Messenger.features.users.dto.UsersDto;
import com.group.Messenger.features.users.models.Users;
import com.group.Messenger.features.users.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Consumer;

@Service
public class UsersServiceImpl implements UsersService{

    @Autowired
    private UsersRepository usersRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtHelper jwtHelper;

    //TODO: implement using bloom filter
    @Override
    public UserDetailsDto createUser(UserDetailsDto userDetailsDto) {
        String currentUserName = userDetailsDto.getUserName();
        Optional<Users> existingUser = usersRepository.findByUserName(currentUserName);
        if (existingUser.isPresent()) {
            throw new CustomGroupMessengerException("Username already exists: " + currentUserName);
        }
        Users user = new Users();
        user.setUserName(userDetailsDto.getUserName());
        user.setEmail(userDetailsDto.getEmail());
        user.setPassword(passwordEncoder.encode(userDetailsDto.getPassword()));
        user.setFirstName(userDetailsDto.getFirstName());
        user.setSecondName(userDetailsDto.getSecondName());
        user.setDateOfBirth(userDetailsDto.getDateOfBirth());
        user.setAddress(userDetailsDto.getAddress());
        user.setProvideType(LoginProviders.SELF);

        Users savedUser = usersRepository.save(user);

        return convertToUserDetailsDto(savedUser);
    }

    @Override
    public String loginUser(String username, String password) {
        Optional<Users>loginUser = usersRepository.findByUserName(username);
        if(loginUser.isEmpty()) {
            throw new CustomGroupMessengerException("Invalid username: " + username);
        }
        Users user = loginUser.get();
        if(!passwordEncoder.matches(password,user.getPassword())) {
            throw new CustomGroupMessengerException("Invalid password, please try again");
        }

        return jwtHelper.generateToken(new org.springframework.security.core.userdetails.User( user.getUserName(), user.getPassword(), new ArrayList<>()));
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
    public Optional<UserDetailsDto> getUsersByEmailId(String email) {
        Optional<Users> user = usersRepository.findByEmail(email);
        if (user.isEmpty()) {
            throw new CustomGroupMessengerException("User does not exist by emailId: " + email);
        }
        return user.map(this::convertToUserDetailsDto);
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
        user.setIsDeleted(true);
        usersRepository.save(user);
    }

    @Override
    public void deleteUsersByUserId(Long userId) {
        Users user = usersRepository.findByUserIdAndIsDeletedFalse(userId)
                .orElseThrow(()->new CustomGroupMessengerException("User not found with userId: " + userId));
        user.setIsDeleted(true);
        usersRepository.save(user);
    }

    private UserDetailsDto convertToUserDetailsDto(Users user) {
        UserDetailsDto userDetailsDto = new UserDetailsDto();
        userDetailsDto.setUserId(user.getUserId());
        userDetailsDto.setEmail(user.getEmail());
        userDetailsDto.setPassword(user.getPassword());
        userDetailsDto.setUserName(user.getUserName());
        userDetailsDto.setFirstName(user.getFirstName());
        userDetailsDto.setSecondName(user.getSecondName());
        userDetailsDto.setDateOfBirth(user.getDateOfBirth());
        userDetailsDto.setAddress(user.getAddress());
        userDetailsDto.setIsDeleted(user.getIsDeleted());
        userDetailsDto.setProviderType(user.getProvideType());
        return userDetailsDto;
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

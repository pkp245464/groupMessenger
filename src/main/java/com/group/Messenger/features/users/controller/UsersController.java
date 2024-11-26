package com.group.Messenger.features.users.controller;

import com.group.Messenger.core.exceptions.CustomGroupMessengerException;
import com.group.Messenger.features.users.dto.UsersDto;
import com.group.Messenger.features.users.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/group-messenger/users")
public class UsersController {

    @Autowired
    private UsersService usersService;

    @PostMapping("/create")
    public ResponseEntity<UsersDto> createUser(@RequestBody UsersDto usersDto) {
        UsersDto createUser = usersService.createUser(usersDto);
        return new ResponseEntity<>(createUser, HttpStatus.CREATED);
    }

    @PutMapping("/update")
    public ResponseEntity<UsersDto> updateUser(@RequestBody UsersDto usersDto) {
        UsersDto updateUser = usersService.updateUser(usersDto);
        return new ResponseEntity<>(updateUser, HttpStatus.OK);
    }

    @GetMapping("/id/{userId}")
    public ResponseEntity<UsersDto> getUserById(@PathVariable Long userId) {
        Optional<UsersDto> userDto = usersService.getUsersById(userId);
        return userDto.map(dto -> new ResponseEntity<>(dto, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/username/{username}")
    public ResponseEntity<UsersDto> getUserByUsername(@PathVariable String username) {
        Optional<UsersDto> userDto = usersService.getUsersByUsername(username);
        return userDto.map(dto -> new ResponseEntity<>(dto, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping("/id/{userId}")
    public ResponseEntity<String> deleteUserByUserId(@PathVariable Long userId) {
        try {
            usersService.deleteUsersByUserId(userId);
            return new ResponseEntity<>("User deleted successfully: " + userId, HttpStatus.NO_CONTENT);
        }
        catch (CustomGroupMessengerException ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/username/{username}")
    public ResponseEntity<String> deleteUserByUsername(@PathVariable String username) {
        try {
            usersService.deleteUsersByUserName(username);
            return new ResponseEntity<>("User deleted successfully: " + username, HttpStatus.NO_CONTENT);
        }
        catch (CustomGroupMessengerException ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
        }
    }
}

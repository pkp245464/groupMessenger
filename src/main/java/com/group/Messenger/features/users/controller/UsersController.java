package com.group.Messenger.features.users.controller;

import com.group.Messenger.features.users.dto.UsersDto;
import com.group.Messenger.features.users.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/group-messenger/users")
public class UsersController {

    @Autowired
    private UsersService usersService;

    @PostMapping
    public ResponseEntity<UsersDto> createUser(@RequestBody UsersDto usersDto) {
        UsersDto createUser = usersService.createUser(usersDto);
        return new ResponseEntity<>(createUser, HttpStatus.CREATED);
    }
}

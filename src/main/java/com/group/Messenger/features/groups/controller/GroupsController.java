package com.group.Messenger.features.groups.controller;

import com.group.Messenger.core.exceptions.CustomGroupMessengerException;
import com.group.Messenger.features.groups.dto.GroupsDto;
import com.group.Messenger.features.groups.service.GroupsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@RestController
@RequestMapping("/group-messenger/groups")
public class GroupsController {

    @Autowired
    private GroupsService groupsService;

    @PostMapping("/create")
    public ResponseEntity<GroupsDto> createGroup(@RequestBody GroupsDto groupsDto) {
        Long creatorUserId = groupsDto.getCreatorUserId();
        GroupsDto createGroup = groupsService.createGroup(groupsDto,creatorUserId);
        return new ResponseEntity<>(createGroup, HttpStatus.CREATED);
    }

    @GetMapping("/id/{groupId}")
    public ResponseEntity<GroupsDto> getGroupByGroupId(@PathVariable Long groupId) {
        Optional<GroupsDto> groupsDto = groupsService.getGroupByGroupId(groupId);
        return groupsDto.map(dto -> new ResponseEntity<>(dto,HttpStatus.OK))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/name/{groupName}")
    public ResponseEntity<GroupsDto> getGroupByGroupId(@PathVariable String groupName) {
        Optional<GroupsDto> groupsDto = groupsService.getGroupByGroupName(groupName);
        return groupsDto.map(dto -> new ResponseEntity<>(dto,HttpStatus.OK))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @PutMapping("/update")
    public ResponseEntity<GroupsDto> updateGroups(@RequestBody GroupsDto groupsDto) {
        GroupsDto updateGroups = groupsService.updateGroups(groupsDto);
        return new ResponseEntity<>(updateGroups,HttpStatus.OK);
    }

    @DeleteMapping("/id/{groupId}")
    public ResponseEntity<String> deleteGroupByGroupId(@PathVariable Long groupId) {
        try {
            groupsService.deleteGroupsByGroupId(groupId);
            return new ResponseEntity<>("groups deleted successfully: " + groupId, HttpStatus.NO_CONTENT);
        }
        catch (CustomGroupMessengerException ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/name/{groupName}")
    public ResponseEntity<String> deleteGroupByGroupName(@PathVariable String groupName) {
        try {
            groupsService.deleteGroupsByGroupName(groupName);
            return new ResponseEntity<>("groups deleted successfully: " + groupName, HttpStatus.NO_CONTENT);
        }
        catch (CustomGroupMessengerException ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
        }
    }
}

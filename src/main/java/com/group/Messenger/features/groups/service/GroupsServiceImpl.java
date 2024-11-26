package com.group.Messenger.features.groups.service;

import com.group.Messenger.core.exceptions.CustomGroupMessengerException;
import com.group.Messenger.features.groups.dto.GroupsDto;
import com.group.Messenger.features.groups.models.Groups;
import com.group.Messenger.features.groups.repository.GroupsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class GroupsServiceImpl implements GroupsService {

    @Autowired
    private GroupsRepository groupsRepository;

    @Override
    public GroupsDto createGroup(GroupsDto groupsDto) {
        String currentGroupName = groupsDto.getGroupName();
        Optional<Groups> existingGroup = groupsRepository.findByGroupName(currentGroupName);
        if(existingGroup.isPresent()) {
            throw new CustomGroupMessengerException("Group with the given name already exists: " + currentGroupName);
        }
        Groups group = new Groups();
        group.setGroupName(groupsDto.getGroupName());
        group.setGroupDescription(groupsDto.getGroupDescription());
        Groups savedGroup = groupsRepository.save(group);
        return convertToDto(savedGroup);
    }

    @Override
    public Optional<GroupsDto> getGroupByGroupId(Long groupId) {
        Optional<Groups>groups = groupsRepository.findByGroupIdAndIsDeletedFalse(groupId);
        if(groups.isEmpty()) {
            throw new CustomGroupMessengerException("Groups does not exist by groupId: " + groupId);
        }
        return groups.map(this::convertToDto);
    }

    @Override
    public Optional<GroupsDto> getGroupByGroupName(String groupName) {
        Optional<Groups>groups = groupsRepository.findByGroupNameAndIsDeletedFalse(groupName);
        if(groups.isEmpty()) {
            throw new CustomGroupMessengerException("Groups does not exist by groupName: " + groupName);
        }
        return groups.map(this::convertToDto);
    }

    @Override
    public GroupsDto updateGroups(Long groupId) {
        return null;
    }

    @Override
    public void deleteGroupsByGroupId(Long groupId) {
        Groups group = groupsRepository.findByGroupIdAndIsDeletedFalse(groupId)
                .orElseThrow(()->new CustomGroupMessengerException("Group not found with groupId: " + groupId));
        group.setIsDeleted(true);
        groupsRepository.save(group);
    }

    @Override
    public void deleteGroupsByGroupName(String groupName) {
        Groups group = groupsRepository.findByGroupNameAndIsDeletedFalse(groupName)
                .orElseThrow(()->new CustomGroupMessengerException("Group not found with groupId: " + groupName));
        group.setIsDeleted(true);
        groupsRepository.save(group);
    }

    private GroupsDto convertToDto(Groups group) {
        GroupsDto groupsDto = new GroupsDto();
        groupsDto.setGroupId(group.getGroupId());
        groupsDto.setGroupName(group.getGroupName());
        groupsDto.setGroupDescription(group.getGroupDescription());
        groupsDto.setIsDeleted(group.getIsDeleted());
        return groupsDto;
    }

}

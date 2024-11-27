package com.group.Messenger.features.groups.service;

import com.group.Messenger.core.enums.GroupEnum;
import com.group.Messenger.core.exceptions.CustomGroupMessengerException;
import com.group.Messenger.features.groups.dto.GroupsDto;
import com.group.Messenger.features.groups.models.Groups;
import com.group.Messenger.features.groups.models.GroupsMembers;
import com.group.Messenger.features.groups.repository.GroupsMembersRepository;
import com.group.Messenger.features.groups.repository.GroupsRepository;
import com.group.Messenger.features.users.models.Users;
import com.group.Messenger.features.users.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.function.Consumer;

@Service
public class GroupsServiceImpl implements GroupsService {

    @Autowired
    private GroupsRepository groupsRepository;

    @Autowired
    private UsersRepository usersRepository;

    @Autowired
    private GroupsMembersRepository groupsMembersRepository;

    //TODO add bloom filter for checking groups

    @Override
    public GroupsDto createGroup(GroupsDto groupsDto, Long creatorUserId) {
        String currentGroupName = groupsDto.getGroupName();
        Optional<Groups> existingGroup = groupsRepository.findByGroupName(currentGroupName);
        if(existingGroup.isPresent()) {
            throw new CustomGroupMessengerException("Group with the given name already exists: " + currentGroupName);
        }
        Groups group = new Groups();
        group.setGroupName(groupsDto.getGroupName());
        group.setGroupDescription(groupsDto.getGroupDescription());
        Groups savedGroup = groupsRepository.save(group);

        Users creator = usersRepository.findByUserIdAndIsDeletedFalse(creatorUserId)
                .orElseThrow(()->new CustomGroupMessengerException("User not found with userId: " + creatorUserId));

        GroupsMembers groupsMembers = new GroupsMembers();
        groupsMembers.setUsers(creator);
        groupsMembers.setGroups(savedGroup);
        groupsMembers.setGroupRole(GroupEnum.admin);

        groupsMembersRepository.save(groupsMembers);

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
    public GroupsDto updateGroups(GroupsDto groupsDto) {
        Long groupId = groupsDto.getGroupId();
        Groups existingGroup = groupsRepository.findByGroupIdAndIsDeletedFalse(groupId)
                .orElseThrow(()->new CustomGroupMessengerException("Group not found with groupId: " + groupId));

        updateFieldIfNotNull(existingGroup::setGroupName,groupsDto.getGroupName());
        updateFieldIfNotNull(existingGroup::setGroupDescription,groupsDto.getGroupDescription());
        updateFieldIfNotNull(existingGroup::setIsDeleted,groupsDto.getIsDeleted());

        Groups updatedGroups = groupsRepository.save(existingGroup);
        return convertToDto(updatedGroups);
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

    @Override
    public void addUserToGroup(Long userId, Long groupId, GroupEnum groupRole) {
        Groups groups = groupsRepository.findByGroupIdAndIsDeletedFalse(groupId)
                .orElseThrow(() -> new CustomGroupMessengerException("Group not found with groupId: " + groupId));
        Users users = usersRepository.findByUserIdAndIsDeletedFalse(userId)
                .orElseThrow(() -> new CustomGroupMessengerException("User not found with userId: " + userId));

        Optional<GroupsMembers> existingMember = groupsMembersRepository.findByUserIdAndGroupId(userId,groupId);
        if(existingMember.isPresent()) {
            throw new CustomGroupMessengerException("User is already a member of the group");
        }

        GroupsMembers groupsMembers = new GroupsMembers();
        groupsMembers.setUsers(users);
        groupsMembers.setGroups(groups);
        groupsMembers.setGroupRole(groupRole);
        
        groupsMembersRepository.save(groupsMembers);
    }

    private <T> void updateFieldIfNotNull(Consumer<T> setter, T value) {
        if (value != null) {
            setter.accept(value);
        }
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

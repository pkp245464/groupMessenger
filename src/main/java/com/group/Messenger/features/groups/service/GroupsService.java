package com.group.Messenger.features.groups.service;

import com.group.Messenger.features.groups.dto.GroupsDto;

import java.util.Optional;

public interface GroupsService {

    GroupsDto createGroup(GroupsDto groupsDto);
    Optional<GroupsDto> getGroupByGroupId(Long groupId);
    Optional<GroupsDto> getGroupByGroupName(String groupName);
    GroupsDto updateGroups(Long groupId);
    void deleteGroupsByGroupId(Long groupId);
    void deleteGroupsByGroupName(String groupName);

}

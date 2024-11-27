package com.group.Messenger.features.groups.service;

import com.group.Messenger.core.enums.GroupEnum;
import com.group.Messenger.features.groups.dto.GroupsDto;

import java.util.Optional;

public interface GroupsService {

    GroupsDto createGroup(GroupsDto groupsDto, Long creatorUserId);
    Optional<GroupsDto> getGroupByGroupId(Long groupId);
    Optional<GroupsDto> getGroupByGroupName(String groupName);
    GroupsDto updateGroups(GroupsDto groupsDto);
    void deleteGroupsByGroupId(Long groupId);
    void deleteGroupsByGroupName(String groupName);
    void addUserToGroup(Long userId,Long groupId, GroupEnum groupRole);
}

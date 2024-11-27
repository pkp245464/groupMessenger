package com.group.Messenger.features.groups.repository;

import com.group.Messenger.features.groups.models.GroupsMembers;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface GroupsMembersRepository extends JpaRepository<GroupsMembers,Long> {

    @Query("SELECT gm FROM GroupsMembers gm WHERE gm.users.userId = :userId AND gm.groups.groupId = :groupId")
    Optional<GroupsMembers> findByUserIdAndGroupId(Long userId, Long groupId);
}

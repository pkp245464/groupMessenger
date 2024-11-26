package com.group.Messenger.features.groups.repository;

import com.group.Messenger.features.groups.models.Groups;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface GroupsRepository extends JpaRepository<Groups,String> {
    Optional<Groups> findByGroupName(String groupName);
    Optional<Groups> findByGroupIdAndIsDeletedFalse(Long groupId);
    Optional<Groups> findByGroupNameAndIsDeletedFalse(String groupName);
}

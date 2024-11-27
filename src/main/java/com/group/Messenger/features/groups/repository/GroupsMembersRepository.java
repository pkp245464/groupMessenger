package com.group.Messenger.features.groups.repository;

import com.group.Messenger.features.groups.models.GroupsMembers;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GroupsMembersRepository extends JpaRepository<GroupsMembers,Long> {
}

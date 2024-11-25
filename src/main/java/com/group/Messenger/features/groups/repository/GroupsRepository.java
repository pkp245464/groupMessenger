package com.group.Messenger.features.groups.repository;

import com.group.Messenger.features.groups.models.Groups;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GroupsRepository extends JpaRepository<Groups,String> {

}

package com.group.Messenger.features.groups.models;

import com.group.Messenger.features.users.models.Users;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "groups_members")
public class GroupsMembers {

    @Id
    @Column(name = "group_members_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long groupMembersId;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private Users users;

    @ManyToOne
    @JoinColumn(name = "group_id")
    private Groups groups;
}

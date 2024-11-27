package com.group.Messenger.features.groups.models;

import com.group.Messenger.core.enums.GroupEnum;
import com.group.Messenger.features.users.models.Users;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "groups_members")
public class GroupsMembers {

    @Id
    @Column(name = "group_members_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long groupMembersId;

    @Enumerated(value = EnumType.STRING)
    @Column(name = "group_role")
    private GroupEnum groupRole;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private Users users;

    @ManyToOne
    @JoinColumn(name = "group_id")
    private Groups groups;
}

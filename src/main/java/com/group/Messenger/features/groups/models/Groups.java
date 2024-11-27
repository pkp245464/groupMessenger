package com.group.Messenger.features.groups.models;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Entity
@Table(name = "groups")
public class Groups {

    @Id
    @Column(name = "group_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long groupId; //groupId must be unique

    @Column(name = "group_name")
    private String groupName; //groupName must be unique

    @Column(name = "group_description")
    private String groupDescription;

    @Column(name = "created_At", insertable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "is_deleted")
    private Boolean isDeleted = false;

    @OneToMany(mappedBy = "groups")
    private List<GroupsMembers>groupsMembers;
}

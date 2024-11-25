package com.group.Messenger.features.users.models;

import com.group.Messenger.features.groups.models.GroupsMembers;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Entity
@Table(name = "users")
public class Users {

    @Id
    @Column(name = "user_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId; //userId must be unique

    @Column(name = "username", insertable = true)
    private String userName; //userName must be unique

    @Column(name = "first_name", insertable = true)
    private String firstName;

    @Column(name = "second_name", insertable = false)
    private String secondName;

    @Column(name = "create_At", insertable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_At", insertable = false)
    private LocalDateTime updatedAt;

    @Column(name = "date_of_birth", insertable = false)
    private LocalDate dateOfBirth;

    @Column(name = "address", insertable = true)
    private String address;

    @Column(name = "is_deleted")
    private Boolean isDeleted = false;

    @OneToMany(mappedBy = "users")
    private List<GroupsMembers> groupsMembers;
}

package com.group.Messenger.features.groups.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class GroupsDto {

    @JsonProperty("groupId")
    private Long groupId;

    @JsonProperty("groupName")
    private String groupName;

    @JsonProperty("groupDescription")
    private String groupDescription;

    @JsonProperty("createAt")
    private LocalDateTime createdAt;

    @JsonProperty("isDeleted")
    private Boolean isDeleted;

    @JsonProperty("groupMembersId")
    private List<Long> groupMembersId;
}

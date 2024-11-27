package com.group.Messenger.features.groups.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.group.Messenger.core.enums.GroupEnum;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AddUserToGroupDto {

    @JsonProperty("userId")
    private Long userId;

    @JsonProperty("groupId")
    private Long groupId;

    @JsonProperty("groupRole")
    private GroupEnum groupRole;
}

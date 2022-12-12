package bsm.speaker.domain.group.dto.response;

import bsm.speaker.domain.user.domain.dto.response.UserResponse;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class GroupResponseDto {

    private String id;
    private String name;
    private String description;
    private List<UserResponse> members;
}

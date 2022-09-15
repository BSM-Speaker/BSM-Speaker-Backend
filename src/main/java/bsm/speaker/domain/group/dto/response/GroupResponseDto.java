package bsm.speaker.domain.group.dto.response;

import bsm.speaker.domain.user.dto.response.UserResponseDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class GroupResponseDto {

    private String id;
    private String name;
    private String description;
    private List<UserResponseDto> members;
}

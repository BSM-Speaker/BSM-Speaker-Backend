package bsm.speaker.domain.post.dto.response;

import bsm.speaker.domain.user.dto.response.UserResponseDto;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class PostResponseDto {

    private long id;
    private String title;
    private UserResponseDto user;
    private int hit;
    private LocalDateTime createdAt;
}

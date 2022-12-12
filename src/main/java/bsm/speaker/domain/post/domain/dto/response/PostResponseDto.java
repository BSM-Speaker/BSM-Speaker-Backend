package bsm.speaker.domain.post.domain.dto.response;

import bsm.speaker.domain.user.domain.dto.response.UserResponse;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class PostResponseDto {

    private long id;
    private String title;
    private String content;
    private UserResponse user;
    private LocalDateTime createdAt;
    private boolean permission;
}

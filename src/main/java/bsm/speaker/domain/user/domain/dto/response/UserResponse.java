package bsm.speaker.domain.user.domain.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UserResponse {

    private Long code;
    private String nickname;
}

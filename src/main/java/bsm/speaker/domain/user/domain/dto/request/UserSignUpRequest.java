package bsm.speaker.domain.user.domain.dto.request;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UserSignUpRequest {

    private Long userCode;
    private String nickname;
}

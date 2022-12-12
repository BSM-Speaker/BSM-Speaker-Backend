package bsm.speaker.domain.user.domain.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UserLoginResponse {

    private String accessToken;
    private String refreshToken;
}

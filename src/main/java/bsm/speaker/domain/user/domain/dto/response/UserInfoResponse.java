package bsm.speaker.domain.user.domain.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UserInfoResponse {

    private long userCode;
    private String nickname;
}

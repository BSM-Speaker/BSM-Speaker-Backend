package bsm.speaker.domain.user.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@Builder
public class UserInfoResponseDto {

    private long userCode;
    private String nickname;
}

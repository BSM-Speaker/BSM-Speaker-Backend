package bsm.speaker.domain.user.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UserSignUpDto {

    private Long userCode;
    private String nickname;
}

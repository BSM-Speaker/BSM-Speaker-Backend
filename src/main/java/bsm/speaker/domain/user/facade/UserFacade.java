package bsm.speaker.domain.user.facade;

import bsm.speaker.domain.user.domain.User;
import bsm.speaker.domain.user.domain.dto.response.UserResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserFacade {

    public UserResponse toBoardUserResponse(User user) {
        return UserResponse.builder()
                .code(user.getUserCode())
                .nickname(user.getNickname())
                .build();
    }

}

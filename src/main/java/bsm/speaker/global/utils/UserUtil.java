package bsm.speaker.global.utils;

import bsm.speaker.global.auth.UserInfo;
import bsm.speaker.domain.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserUtil {

    public User getCurrentUser() {
        UserInfo userInfo = (UserInfo) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return userInfo.getUser();
    }
}

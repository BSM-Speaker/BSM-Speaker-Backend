package bsm.speaker.domain.user.domain.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
@AllArgsConstructor
public class BsmOauthTokenResponse {

    private String token;
}

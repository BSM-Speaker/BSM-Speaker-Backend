package bsm.speaker.domain.user.domain.dto.response;

import bsm.speaker.domain.user.domain.dto.request.UserSignUpRequest;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Map;

@Getter
@RequiredArgsConstructor
@AllArgsConstructor
public class BsmOauthResourceResponse {

    private UserSignUpRequest user;

    @JsonProperty("user")
    private void unpackNested(Map<String, Object> user) {
        this.user = UserSignUpRequest.builder()
                .userCode(Long.parseLong(user.get("code").toString()))
                .nickname(user.get("nickname").toString())
                .build();
    }
}

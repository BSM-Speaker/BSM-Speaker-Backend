package bsm.speaker.domain.user.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Map;

@Getter
@RequiredArgsConstructor
@AllArgsConstructor
public class BsmOauthResourceResponseDto {

    private UserSignUpDto user;

    @JsonProperty("user")
    private void unpackNested(Map<String, Object> user) {
        this.user = UserSignUpDto.builder()
                .userCode(Long.parseLong(user.get("code").toString()))
                .nickname(user.get("nickname").toString())
                .build();
    }
}

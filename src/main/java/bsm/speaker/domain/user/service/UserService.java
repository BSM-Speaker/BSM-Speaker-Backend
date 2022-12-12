package bsm.speaker.domain.user.service;

import bsm.speaker.domain.user.domain.dto.response.BsmOauthTokenResponse;
import bsm.speaker.domain.user.domain.dto.request.UserSignUpRequest;
import bsm.speaker.global.error.exceptions.NotFoundException;
import bsm.speaker.domain.user.domain.dto.response.BsmOauthResourceResponse;
import bsm.speaker.domain.user.domain.User;
import bsm.speaker.domain.user.domain.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import okhttp3.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final OkHttpClient httpClient;
    private final ObjectMapper objectMapper;

    @Value("${env.oauth.bsm.client.id}")
    private String OAUTH_BSM_CLIENT_ID;
    @Value("${env.oauth.bsm.client.secret}")
    private String OAUTH_BSM_CLIENT_SECRET;
    @Value("${env.oauth.bsm.url.token}")
    private String OAUTH_BSM_TOKEN_URL;
    @Value("${env.oauth.bsm.url.resource}")
    private String OAUTH_BSM_RESOURCE_URL;

    @Transactional
    public User signUp(UserSignUpRequest dto, String oauthToken) {
        User user = User.builder()
                .userCode(dto.getUserCode())
                .nickname(dto.getNickname())
                .oauthToken(oauthToken)
                .build();
        return userRepository.save(user);
    }

    @Transactional
    public User bsmOauth(String authCode) throws IOException {
        // Payload
        Map<String, String> getTokenPayload = new HashMap<>();
        getTokenPayload.put("clientId", OAUTH_BSM_CLIENT_ID);
        getTokenPayload.put("clientSecret", OAUTH_BSM_CLIENT_SECRET);
        getTokenPayload.put("authCode", authCode);

        // Request
        Request tokenRequest = new Request.Builder()
                .url(OAUTH_BSM_TOKEN_URL)
                .post(RequestBody.create(MediaType.parse("application/json"), objectMapper.writeValueAsString(getTokenPayload)))
                .build();
        Response tokenResponse = httpClient.newCall(tokenRequest).execute();
        if (tokenResponse.code() == 404) {
            throw new NotFoundException("인증 코드를 찾을 수 없습니다");
        }
        BsmOauthTokenResponse tokenResponseDto = objectMapper.readValue(Objects.requireNonNull(tokenResponse.body()).string(), BsmOauthTokenResponse.class);

        // Payload
        Map<String, String> getResourcePayload = new HashMap<>();
        getResourcePayload.put("clientId", OAUTH_BSM_CLIENT_ID);
        getResourcePayload.put("clientSecret", OAUTH_BSM_CLIENT_SECRET);
        getResourcePayload.put("token", tokenResponseDto.getToken());

        // Request
        Request resourceRequest = new Request.Builder()
                .url(OAUTH_BSM_RESOURCE_URL)
                .post(RequestBody.create(MediaType.parse("application/json"), objectMapper.writeValueAsString(getResourcePayload)))
                .build();
        Response resourceResponse = httpClient.newCall(resourceRequest).execute();
        BsmOauthResourceResponse resourceResponseDto = objectMapper.readValue(Objects.requireNonNull(resourceResponse.body()).string(), BsmOauthResourceResponse.class);

        // 없는 유저면 회원가입 후 유저 리턴, 이미 있으면 유저를 바로 리턴
        return userRepository.findById(resourceResponseDto.getUser().getUserCode()).orElseGet(
                () -> signUp(resourceResponseDto.getUser(), tokenResponseDto.getToken())
        );
    }
}

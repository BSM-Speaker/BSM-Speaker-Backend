package bsm.speaker.domain.webpush.service;

import bsm.speaker.domain.user.domain.User;
import bsm.speaker.domain.webpush.domain.WebPush;
import bsm.speaker.domain.webpush.domain.repository.WebPushRepository;
import bsm.speaker.domain.webpush.presentation.dto.request.WebPushSubscribeRequest;
import bsm.speaker.global.error.exceptions.NotFoundException;
import bsm.speaker.global.utils.UserUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class WebPushService {

    private final UserUtil userUtil;
    private final WebPushRepository webpushRepository;

    public void subscribe(WebPushSubscribeRequest dto) {
        User user = userUtil.getCurrentUser();

        WebPush webPush = webpushRepository.findByUserCodeAndEndpoint(user.getUserCode(), dto.getEndpoint())
                .orElseGet(() -> WebPush.builder()
                        .endpoint(dto.getEndpoint())
                        .auth(dto.getAuth())
                        .p256dh(dto.getP256dh())
                        .userCode(user.getUserCode())
                        .build()
                );

        webPush.updateWebPush(dto);
        webpushRepository.save(webPush);
    }

    public void unsubscribe(String endpoint) {
        WebPush webPush = webpushRepository.findById(endpoint).orElseThrow(NotFoundException::new);
        webpushRepository.delete(webPush);
    }

}

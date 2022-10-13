package bsm.speaker.domain.webpush.service;

import bsm.speaker.domain.user.entities.User;
import bsm.speaker.domain.webpush.domain.WebPush;
import bsm.speaker.domain.webpush.domain.repository.WebPushRepository;
import bsm.speaker.domain.webpush.presentation.dto.request.WebPushSubscribeRequest;
import bsm.speaker.global.utils.UserUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

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

    public void unsubscribe() {
        List<WebPush> webPushList = webpushRepository.findAllByUserCode(userUtil.getCurrentUser().getUserCode());
        webpushRepository.deleteAll(webPushList);
    }

}

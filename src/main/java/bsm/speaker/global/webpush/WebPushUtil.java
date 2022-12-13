package bsm.speaker.global.webpush;

import bsm.speaker.global.async.service.AsyncService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import bsm.speaker.domain.webpush.domain.WebPush;
import bsm.speaker.domain.webpush.presentation.dto.request.WebPushSendDto;
import lombok.RequiredArgsConstructor;
import nl.martijndwars.webpush.Notification;
import nl.martijndwars.webpush.PushService;
import nl.martijndwars.webpush.Subscription;
import org.jose4j.lang.JoseException;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.List;
import java.util.concurrent.ExecutionException;

@Component
@RequiredArgsConstructor
public class WebPushUtil {

    private final ObjectMapper objectMapper;
    private final PushService pushService;
    private final AsyncService asyncService;

    @Async
    public void sendNotification(WebPush webPush, WebPushSendDto dto) throws Exception {
        Subscription subscription = toSubscription(webPush);
        String msg = objectMapper.writeValueAsString(dto);
        send(subscription, msg);
    }

    @Async
    public void sendNotificationToAll(List<WebPush> webPushList, WebPushSendDto dto) throws JsonProcessingException {
        List<Subscription> subscriptionList = webPushList.stream()
                .map(this::toSubscription)
                .toList();
        String msg = objectMapper.writeValueAsString(dto);

        subscriptionList.forEach(subscription -> send(subscription, msg));
    }

    public void send(Subscription subscription, String msg) {
        asyncService.run(() -> {
            try {
                pushService.send(new Notification(subscription, msg));
            } catch (Exception ignored) {}
        });
    }

    private Subscription toSubscription(WebPush webPush) {
        return new Subscription(webPush.getEndpoint(), new Subscription.Keys(webPush.getP256dh(), webPush.getAuth()));
    }

}

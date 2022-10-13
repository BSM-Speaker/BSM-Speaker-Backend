package bsm.speaker.global.webpush;

import bsm.speaker.global.exceptions.InternalServerException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import bsm.speaker.domain.webpush.domain.WebPush;
import bsm.speaker.domain.webpush.presentation.dto.request.WebPushSendDto;
import lombok.RequiredArgsConstructor;
import nl.martijndwars.webpush.Notification;
import nl.martijndwars.webpush.PushService;
import nl.martijndwars.webpush.Subscription;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class WebPushUtil {

    private final ObjectMapper objectMapper;
    private final PushService pushService;

    public void sendNotification(WebPush webPush, WebPushSendDto dto) throws Exception {
        Subscription subscription = new Subscription(webPush.getEndpoint(), new Subscription.Keys(webPush.getP256dh(), webPush.getAuth()));
        pushService.send(new Notification(subscription, objectMapper.writeValueAsString(dto)));
    }

    public void sendNotificationToAll(List<WebPush> webPushList, WebPushSendDto dto) throws JsonProcessingException {
        List<Subscription> subscriptionList = webPushList.stream()
                .map(webPush -> new Subscription(webPush.getEndpoint(), new Subscription.Keys(webPush.getP256dh(), webPush.getAuth())))
                .toList();
        String msg = objectMapper.writeValueAsString(dto);

        subscriptionList.forEach(subscription -> {
            try {
                pushService.send(new Notification(subscription, msg));
            } catch (Exception ignored) {}
        });
    }

}

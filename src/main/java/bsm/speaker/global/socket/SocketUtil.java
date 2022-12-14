package bsm.speaker.global.socket;

import bsm.speaker.domain.user.domain.User;
import bsm.speaker.global.async.service.AsyncService;
import bsm.speaker.global.socket.domain.SocketEvent;
import bsm.speaker.global.socket.service.SocketClientProvider;
import com.corundumstudio.socketio.SocketIOClient;
import io.netty.handler.codec.http.cookie.ClientCookieDecoder;
import io.netty.handler.codec.http.cookie.Cookie;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class SocketUtil {

    private final SocketClientProvider socketClientProvider;
    private final AsyncService asyncService;

    public void sendMessageToUser(User user, SocketEvent socketEvent, Object msg) {
        List<SocketIOClient> clientList = socketClientProvider.findAllByUser(user);
        clientList.forEach(client ->
                asyncService.run(() -> client.sendEvent(String.valueOf(socketEvent), msg))
        );
    }

    public Cookie getCookieByCookieHeader(List<String> cookieHeader, String name) {
        List<Cookie> cookies = cookieHeader.stream()
                .map(ClientCookieDecoder.LAX::decode)
                .toList();
        if (cookies == null) return null;
        for (Cookie cookie : cookies) {
            if (cookie.name().equals(name)) {
                return cookie;
            }
        }
        return null;
    }
}

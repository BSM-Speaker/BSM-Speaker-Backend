package bsm.speaker.global.socket.service;

import bsm.speaker.domain.user.domain.User;
import bsm.speaker.domain.user.domain.UserRepository;
import bsm.speaker.global.auth.UserInfo;
import bsm.speaker.global.error.exceptions.NotFoundException;
import bsm.speaker.global.socket.SocketUtil;
import bsm.speaker.global.socket.domain.SocketEvent;
import bsm.speaker.global.utils.JwtUtil;
import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.SocketIOServer;
import io.netty.handler.codec.http.HttpHeaderNames;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

@Service
@RequiredArgsConstructor
public class SocketService {

    private final SocketClientProvider socketClientProvider;
    private final SocketUtil socketUtil;
    private final JwtUtil jwtUtil;
    private final UserRepository userRepository;
    private final SocketIOServer socketIOServer;

    @Value("${env.cookie.name.token}")
    private String TOKEN_COOKIE_NAME;


    @PostConstruct
    private void autoStartup() {
        socketIOServer.addConnectListener(this::onConnect);

        socketIOServer.addDisconnectListener(socketClientProvider::removeClient);

        socketIOServer.start();
    }

    @PreDestroy
    private void autoStop() {
        socketIOServer.stop();
    }

    private void onConnect(SocketIOClient client) {
        String cookieHeaders = client.getHandshakeData().getHttpHeaders()
                .get(HttpHeaderNames.COOKIE);
        try {
            String token = socketUtil.getCookieByCookieHeader(cookieHeaders, TOKEN_COOKIE_NAME).value();
            User user = userRepository.findById(
                    new UserInfo(jwtUtil.getUser(token)).getUser().getUserCode()
            ).orElseThrow(NotFoundException::new);
            socketClientProvider.addClient(user, client);
        } catch (Exception e) {
            client.sendEvent(SocketEvent.UNAUTHORIZED);
            client.disconnect();
        }
    }

}
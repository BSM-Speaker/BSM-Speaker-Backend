package bsm.speaker.global.socket.service;

import bsm.speaker.domain.user.domain.User;
import com.corundumstudio.socketio.SocketIOClient;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class SocketClientProvider {

    private final Map<Long, List<SocketIOClient>> userClientMap = new ConcurrentHashMap<>();
    private final Map<SocketIOClient, User> clientMap = new ConcurrentHashMap<>();

    public void addClient(User user, SocketIOClient client) {
        List<SocketIOClient> clientList = Optional.ofNullable(userClientMap.get(user.getUserCode()))
                .orElseGet(ArrayList::new);
        clientList.add(client);
        userClientMap.put(user.getUserCode(), clientList);
        clientMap.put(client, user);
    }

    public void removeClient(SocketIOClient client) {
        Optional<User> userOptional = Optional.ofNullable(clientMap.get(client));
        if (userOptional.isEmpty()) return;
        User user = userOptional.get();

        List<SocketIOClient> clientList = Optional.ofNullable(userClientMap.get(user.getUserCode()))
                .orElseGet(ArrayList::new);
        clientList.remove(client);
        userClientMap.put(user.getUserCode(), clientList);
        clientMap.remove(client);
    }

    public List<SocketIOClient> findAllByUser(User user) {
        return Optional.ofNullable(userClientMap.get(user.getUserCode()))
                .orElseGet(ArrayList::new);
    }

}

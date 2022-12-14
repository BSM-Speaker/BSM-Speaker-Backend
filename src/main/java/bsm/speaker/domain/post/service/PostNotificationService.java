package bsm.speaker.domain.post.service;

import bsm.speaker.domain.group.entities.Member;
import bsm.speaker.domain.group.repositories.MemberRepository;
import bsm.speaker.domain.post.domain.Post;
import bsm.speaker.domain.user.domain.User;
import bsm.speaker.domain.user.facade.UserFacade;
import bsm.speaker.domain.webpush.domain.WebPush;
import bsm.speaker.domain.webpush.domain.repository.WebPushRepository;
import bsm.speaker.domain.webpush.presentation.dto.request.WebPushSendDto;
import bsm.speaker.global.socket.domain.SocketEvent;
import bsm.speaker.global.socket.service.SocketClientProvider;
import bsm.speaker.global.webpush.WebPushUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class PostNotificationService {

    private final SocketClientProvider socketClientProvider;
    private final WebPushUtil webPushUtil;
    private final WebPushRepository webPushRepository;
    private final MemberRepository memberRepository;
    private final UserFacade userFacade;

    public void sendNewPostNotification(Post post) throws JsonProcessingException {
        List<User> userList = memberRepository.findAllByPkGroupId(post.getGroupId())
                .stream().map(Member::getUser)
                .toList();

        sendNewPostSocketNotification(userList, post);
        sendNewPostPushNotification(userList, post);
    }

    private void sendNewPostPushNotification(List<User> userList, Post post) throws JsonProcessingException {
        WebPushSendDto webPushSendDto = WebPushSendDto.builder()
                .title("새로운 긴급 정보가 올라왔습니다")
                .body(post.getTitle())
                .link("https://speaker.bssm.kro.kr/group/"+post.getGroupId())
                .build();

        List<WebPush> webPushList = webPushRepository.findAllByUserIn(userList);
        webPushUtil.sendNotificationToAll(webPushList, webPushSendDto);
    }

    private void sendNewPostSocketNotification(List<User> userList, Post post) {
        userList.forEach(user ->
                socketClientProvider.findAllByUser(user).forEach(client ->
                    client.sendEvent(SocketEvent.NEWPOST, post.toResponse(userFacade, user))
                )
        );
    }

}

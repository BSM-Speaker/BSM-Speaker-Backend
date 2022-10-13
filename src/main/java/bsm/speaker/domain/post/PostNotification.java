package bsm.speaker.domain.post;

import bsm.speaker.domain.group.entities.Member;
import bsm.speaker.domain.group.repositories.MemberRepository;
import bsm.speaker.domain.user.entities.User;
import bsm.speaker.domain.webpush.domain.WebPush;
import bsm.speaker.domain.webpush.domain.repository.WebPushRepository;
import bsm.speaker.domain.webpush.presentation.dto.request.WebPushSendDto;
import bsm.speaker.global.webpush.WebPushUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class PostNotification {

    private final WebPushUtil webPushUtil;
    private final WebPushRepository webPushRepository;
    private final MemberRepository memberRepository;

    public void sendNewPostNotification(Post post) throws JsonProcessingException {
        WebPushSendDto webPushSendDto = WebPushSendDto.builder()
                .title("새로운 긴급 정보가 올라왔습니다")
                .body(post.getTitle())
                .link("http://localhost:3000/detail/"+post.getGroupId())
                .build();
        List<User> userList = memberRepository.findAllByPkGroupId(post.getGroupId())
                .stream().map(Member::getUser)
                .toList();
        List<WebPush> webPushList = webPushRepository.findAllByUserIn(userList);

        webPushUtil.sendNotificationToAll(webPushList, webPushSendDto);
    }

}

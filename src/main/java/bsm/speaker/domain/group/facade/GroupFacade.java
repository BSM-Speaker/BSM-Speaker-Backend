package bsm.speaker.domain.group.facade;

import bsm.speaker.domain.group.domain.Group;
import bsm.speaker.domain.group.domain.MemberRepository;
import bsm.speaker.domain.post.domain.Post;
import bsm.speaker.domain.post.domain.PostRepository;
import bsm.speaker.domain.user.domain.User;
import bsm.speaker.global.error.exceptions.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class GroupFacade {

    private final MemberRepository memberRepository;

    public Group getGroup(User user, String groupId) {
        return memberRepository.findByPkUserCodeAndGroupId(user.getUserCode(), groupId)
                .orElseThrow(() -> new NotFoundException("그룹을 찾을 수 없습니다"))
                .getGroup();
    }

}

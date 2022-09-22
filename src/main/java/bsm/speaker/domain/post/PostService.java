package bsm.speaker.domain.post;

import bsm.speaker.domain.group.repositories.GroupRepository;
import bsm.speaker.domain.group.repositories.MemberRepository;
import bsm.speaker.domain.post.dto.request.PostWriteRequestDto;
import bsm.speaker.domain.user.entities.User;
import bsm.speaker.global.exceptions.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final MemberRepository memberRepository;

    public void writePost(User user, PostWriteRequestDto dto) {
        if (memberRepository.findByPkUserCodeAndGroupId(user.getUserCode(), dto.getGroupId()).isEmpty()) {
            throw new NotFoundException("그룹을 찾을 수 없습니다");
        }

        Post newPost = Post.builder()
                .title(dto.getTitle())
                .content(dto.getContent())
                .userCode(user.getUserCode())
                .groupId(dto.getGroupId())
                .build();
        postRepository.save(newPost);
    }
}

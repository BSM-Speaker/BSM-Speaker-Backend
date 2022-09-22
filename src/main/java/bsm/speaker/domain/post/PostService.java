package bsm.speaker.domain.post;

import bsm.speaker.domain.group.repositories.GroupRepository;
import bsm.speaker.domain.group.repositories.MemberRepository;
import bsm.speaker.domain.post.dto.request.PostWriteRequestDto;
import bsm.speaker.domain.post.dto.response.PostResponseDto;
import bsm.speaker.domain.user.dto.response.UserResponseDto;
import bsm.speaker.domain.user.entities.User;
import bsm.speaker.global.exceptions.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final MemberRepository memberRepository;

    public List<PostResponseDto> postList(String groupId) {
        return postRepository.findByGroupId(groupId).stream().map(
                post -> PostResponseDto.builder()
                        .id(post.getId())
                        .title(post.getTitle())
                        .hit(post.getHit())
                        .user(
                                UserResponseDto.builder()
                                        .code(post.getUserCode())
                                        .nickname(post.getUser().getNickname())
                                        .build()
                        )
                        .createdAt(post.getCreatedAt())
                        .build()
        ).collect(Collectors.toList());
    }

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

package bsm.speaker.domain.post;

import bsm.speaker.domain.group.repositories.MemberRepository;
import bsm.speaker.domain.post.dto.request.PostListRequestDto;
import bsm.speaker.domain.post.dto.request.PostWriteRequestDto;
import bsm.speaker.domain.post.dto.response.PostResponseDto;
import bsm.speaker.domain.user.dto.response.UserResponseDto;
import bsm.speaker.domain.user.entities.User;
import bsm.speaker.global.error.exceptions.ForbiddenException;
import bsm.speaker.global.error.exceptions.NotFoundException;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@Validated
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final MemberRepository memberRepository;
    private final PostNotification postNotification;

    public List<PostResponseDto> postList(@Valid PostListRequestDto dto) {
        Pageable pageable = PageRequest.of(dto.getPage() - 1, dto.getLimit());
        return postRepository.findByGroupIdOrderByIdDesc(dto.getGroupId(), pageable).stream().map(
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

    public void writePost(User user, PostWriteRequestDto dto) throws JsonProcessingException {
        if (memberRepository.findByPkUserCodeAndGroupId(user.getUserCode(), dto.getGroupId()).isEmpty()) {
            throw new NotFoundException("그룹을 찾을 수 없습니다");
        }

        Post newPost = Post.builder()
                .title(dto.getTitle())
                .content(dto.getContent())
                .userCode(user.getUserCode())
                .groupId(dto.getGroupId())
                .build();
        Post post = postRepository.save(newPost);
        postNotification.sendNewPostNotification(post);
    }

    public void deletePost(User user, long postId) {
        Post post = postRepository.findById(postId).orElseThrow(
                () -> {throw new NotFoundException("게시글을 찾을 수 없습니다");}
        );
        if (!Objects.equals(post.getUserCode(), user.getUserCode())) {
            throw new ForbiddenException("권한이 없습니다");
        }
        postRepository.delete(post);
    }

}

package bsm.speaker.domain.post.service;

import bsm.speaker.domain.group.repositories.MemberRepository;
import bsm.speaker.domain.post.domain.Post;
import bsm.speaker.domain.post.domain.PostRepository;
import bsm.speaker.domain.post.domain.dto.request.PostListRequestDto;
import bsm.speaker.domain.post.domain.dto.request.PostWriteRequestDto;
import bsm.speaker.domain.post.domain.dto.response.PostResponseDto;
import bsm.speaker.domain.user.domain.User;
import bsm.speaker.domain.user.facade.UserFacade;
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

@Service
@Validated
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final MemberRepository memberRepository;
    private final PostNotificationService postNotification;
    private final UserFacade userFacade;

    public List<PostResponseDto> postList(@Valid PostListRequestDto dto, User user) {
        Pageable pageable = PageRequest.of(dto.getPage() - 1, dto.getLimit());
        return postRepository.findByGroupIdOrderByIdDesc(dto.getGroupId(), pageable).stream()
                .map(post -> post.toResponse(userFacade, post.getUser()))
                .toList();
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

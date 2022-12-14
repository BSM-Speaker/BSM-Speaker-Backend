package bsm.speaker.domain.post.service;

import bsm.speaker.domain.group.domain.Group;
import bsm.speaker.domain.group.facade.GroupFacade;
import bsm.speaker.domain.post.domain.Post;
import bsm.speaker.domain.post.domain.PostViewer;
import bsm.speaker.domain.post.domain.PostViewerRepository;
import bsm.speaker.domain.post.domain.dto.request.PostListRequestDto;
import bsm.speaker.domain.post.domain.dto.request.PostViewRequestDto;
import bsm.speaker.domain.post.domain.dto.request.PostWriteRequestDto;
import bsm.speaker.domain.post.domain.dto.response.PostResponseDto;
import bsm.speaker.domain.post.facade.PostFacade;
import bsm.speaker.domain.user.domain.User;
import bsm.speaker.domain.user.facade.UserFacade;
import bsm.speaker.global.error.exceptions.ForbiddenException;
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

    private final PostFacade postFacade;
    private final GroupFacade groupFacade;
    private final UserFacade userFacade;
    private final PostNotificationService postNotification;
    private final PostViewerRepository postViewerRepository;

    public List<PostResponseDto> postList(@Valid PostListRequestDto dto, User user) {
        Group group = groupFacade.getGroup(user, dto.getGroupId());
        Pageable pageable = PageRequest.of(dto.getPage() - 1, dto.getLimit());
        return postFacade.getPostList(group, pageable).stream()
                .map(post -> post.toResponse(userFacade, post.getUser()))
                .toList();
    }

    public void writePost(User user, PostWriteRequestDto dto) throws JsonProcessingException {
        Group group = groupFacade.getGroup(user, dto.getGroupId());

        Post newPost = Post.builder()
                .title(dto.getTitle())
                .content(dto.getContent())
                .userCode(user.getUserCode())
                .group(group)
                .build();
        Post post = postFacade.save(newPost);
        postNotification.sendNewPostNotification(post);
    }

    public void deletePost(User user, long postId) {
        Post post = postFacade.findById(postId);
        if (!Objects.equals(post.getUserCode(), user.getUserCode())) {
            throw new ForbiddenException("권한이 없습니다");
        }
        postFacade.delete(post);
    }

    public void viewPost(User user, @Valid PostViewRequestDto dto) {
        Group group = groupFacade.getGroup(user, dto.getGroupId());
        Post post = postFacade.findByIdAndGroup(dto.getPostId(), group);
        PostViewer postViewer = PostViewer.builder()
                .post(post)
                .user(user)
                .build();
        postViewerRepository.save(postViewer);
    }

}

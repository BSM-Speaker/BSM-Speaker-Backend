package bsm.speaker.domain.post.facade;

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
public class PostFacade {

    private final PostRepository postRepository;

    public Post findById(Long id) {
        return postRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("게시글을 찾을 수 없습니다"));
    }

    public Post findByIdAndGroup(Long id, Group group) {
        return postRepository.findByIdAndGroup(id, group)
                .orElseThrow(() -> new NotFoundException("게시글을 찾을 수 없습니다"));
    }

    public Page<Post> getPostList(Group group, Pageable pageable) {
        return postRepository.findByGroupOrderByIdDesc(group, pageable);
    }

    public Post save(Post post) {
        return postRepository.save(post);
    }

    public void delete(Post post) {
        postRepository.delete(post);
    }

}

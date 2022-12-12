package bsm.speaker.domain.post.domain;

import bsm.speaker.domain.post.domain.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {

    Page<Post> findByGroupIdOrderByIdDesc(String groupId, Pageable pageable);
}

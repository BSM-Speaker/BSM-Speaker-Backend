package bsm.speaker.domain.post.domain;

import bsm.speaker.domain.group.domain.Group;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PostRepository extends JpaRepository<Post, Long> {

    Page<Post> findByGroupOrderByIdDesc(Group group, Pageable pageable);

    @EntityGraph(attributePaths = {
            "viewers",
            "viewers.user"
    })
    Optional<Post> findById(long id);

    @EntityGraph(attributePaths = {
            "viewers",
            "viewers.user"
    })
    Optional<Post> findByIdAndGroup(long id, Group group);
}

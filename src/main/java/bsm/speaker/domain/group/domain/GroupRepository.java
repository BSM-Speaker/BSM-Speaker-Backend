package bsm.speaker.domain.group.domain;

import bsm.speaker.domain.group.domain.Group;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface GroupRepository extends JpaRepository<Group, String> {

    @EntityGraph(attributePaths = "members")
    Optional<Group> findByName(String name);
}

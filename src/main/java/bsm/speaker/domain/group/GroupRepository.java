package bsm.speaker.domain.group;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface GroupRepository extends JpaRepository<Group, String> {

    Optional<Group> findByName(String name);
}

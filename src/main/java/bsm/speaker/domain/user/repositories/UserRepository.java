package bsm.speaker.domain.user.repositories;

import bsm.speaker.domain.user.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface UserRepository extends JpaRepository <User, Long> {

    Optional<User> findByNickname(String nickname);
}

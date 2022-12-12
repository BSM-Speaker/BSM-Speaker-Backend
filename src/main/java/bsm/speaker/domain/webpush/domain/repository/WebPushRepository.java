package bsm.speaker.domain.webpush.domain.repository;

import bsm.speaker.domain.user.domain.User;
import bsm.speaker.domain.webpush.domain.WebPush;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface WebPushRepository extends JpaRepository<WebPush, String> {

    Optional<WebPush> findByUserCodeAndEndpoint(long userCode, String endpoint);

    List<WebPush> findAllByUserCode(long userCode);

    List<WebPush> findAllByUserIn(List<User> userList);

}

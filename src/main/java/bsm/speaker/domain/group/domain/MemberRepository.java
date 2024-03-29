package bsm.speaker.domain.group.domain;

import bsm.speaker.domain.group.domain.Member;
import bsm.speaker.domain.group.domain.MemberPk;
import bsm.speaker.domain.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, MemberPk> {

    List<Member> findAllByUser(User user);

    Optional<Member> findByPkUserCodeAndGroupId(long userCode, String groupId);

    List<Member> findAllByPkGroupId(String groupId);

}

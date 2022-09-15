package bsm.speaker.domain.group.repositories;

import bsm.speaker.domain.group.entities.Group;
import bsm.speaker.domain.group.entities.Member;
import bsm.speaker.domain.group.entities.MemberPk;
import bsm.speaker.domain.user.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MemberRepository extends JpaRepository<Member, MemberPk> {

    List<Member> findAllByUser(User user);
}

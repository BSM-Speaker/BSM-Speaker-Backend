package bsm.speaker.domain.group.repositories;

import bsm.speaker.domain.group.entities.Member;
import bsm.speaker.domain.group.entities.MemberPk;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, MemberPk> {}

package bsm.speaker.domain.group.entities;

import bsm.speaker.domain.user.entities.User;
import bsm.speaker.global.entity.BaseTimeEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Member extends BaseTimeEntity {

    @EmbeddedId
    @JoinColumns({
            @JoinColumn(name = "groupId", insertable = false, updatable = false),
            @JoinColumn(name = "userCode", insertable = false, updatable = false)
    })
    private MemberPk pk;

    @ManyToOne
    @JoinColumn(name = "groupId", insertable = false, updatable = false)
    private Group group;

    @ManyToOne
    @JoinColumn(name = "userCode", insertable = false, updatable = false)
    private User user;

    @Builder
    public Member(MemberPk pk, Group group, Long userCode, User user) {
        this.pk = pk;
        this.group = group;
        this.user = user;
    }
}

package bsm.speaker.domain.group.entities;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Getter
@RequiredArgsConstructor
@Embeddable
public class MemberPk implements Serializable {

    @Column(length = 6)
    private String groupId;

    @Column(columnDefinition = "INT UNSIGNED")
    private Long userCode;

    @Builder
    public MemberPk(String groupId, Long userCode) {
        this.groupId = groupId;
        this.userCode = userCode;
    }
}

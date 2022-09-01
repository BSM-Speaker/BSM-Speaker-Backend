package bsm.speaker.domain.user.entities;

import bsm.speaker.global.entity.BaseTimeEntity;
import lombok.*;

import javax.persistence.*;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RefreshToken extends BaseTimeEntity {

    @Id
    @Column(length = 64)
    private String token;

    @Column(nullable = false)
    private boolean isAvailable;

    @Column(columnDefinition = "INT UNSIGNED")
    private Long userCode;

    @OneToOne
    @JoinColumn(name = "userCode", insertable = false, updatable = false)
    private User user;

    @Builder
    public RefreshToken(String token, boolean isAvailable, Long userCode, User user) {
        this.token = token;
        this.isAvailable = isAvailable;
        this.userCode = userCode;
        this.user = user;
    }
}

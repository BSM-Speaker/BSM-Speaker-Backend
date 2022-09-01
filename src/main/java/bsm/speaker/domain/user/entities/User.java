package bsm.speaker.domain.user.entities;

import bsm.speaker.global.entity.BaseTimeEntity;
import lombok.*;

import javax.persistence.*;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User extends BaseTimeEntity {

    @Id
    @Column(columnDefinition = "INT UNSIGNED")
    private Long userCode;

    @Column(nullable = false, length = 40, unique = true)
    private String nickname;

    @Column(nullable = false, length = 32)
    private String oauthToken;

    @Builder
    public User(Long userCode, String nickname, String oauthToken) {
        this.userCode = userCode;
        this.nickname = nickname;
        this.oauthToken = oauthToken;
    }
}

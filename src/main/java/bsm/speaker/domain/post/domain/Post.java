package bsm.speaker.domain.post.domain;

import bsm.speaker.domain.group.entities.Group;
import bsm.speaker.domain.user.domain.User;
import bsm.speaker.global.entity.BaseTimeEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Post extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(columnDefinition = "INT UNSIGNED")
    private long id;

    @Column(length = 6)
    private String groupId;

    @ManyToOne
    @JoinColumn(name = "groupId", nullable = false, insertable = false, updatable = false)
    private Group group;

    @Column(columnDefinition = "INT UNSIGNED")
    private Long userCode;

    @OneToOne
    @JoinColumn(name = "userCode", insertable = false, updatable = false)
    private User user;

    @Column(nullable = false, length = 50)
    private String title;

    @Column(nullable = false, columnDefinition = "MEDIUMTEXT")
    private String content;

    @Builder
    public Post(long id, String groupId, Group group, Long userCode, User user, String title, String content) {
        this.id = id;
        this.groupId = groupId;
        this.group = group;
        this.userCode = userCode;
        this.user = user;
        this.title = title;
        this.content = content;
    }
}

package bsm.speaker.domain.post;

import bsm.speaker.domain.group.entities.Group;
import bsm.speaker.domain.user.entities.User;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(columnDefinition = "INT UNSIGNED")
    private long id;

    @Column(length = 6)
    private String groupId;

    @ManyToOne
    @JoinColumn(name = "groupId", nullable = false, insertable = false, updatable = false)
    private Group group;

    @Column(name = "isDelete", nullable = false)
    @ColumnDefault("0")
    private boolean delete;

    @Column(columnDefinition = "INT UNSIGNED")
    private Long userCode;

    @OneToOne
    @JoinColumn(name = "userCode", insertable = false, updatable = false)
    private User user;

    @Column(nullable = false, length = 50)
    private String title;

    @Column(nullable = false, columnDefinition = "MEDIUMTEXT")
    private String content;

    @Column(nullable = false, columnDefinition = "INT UNSIGNED")
    @ColumnDefault("0")
    private int hit;

    @Builder
    public Post(long id, String groupId, Group group, boolean delete, Long userCode, User user, String title, String content, int hit) {
        this.id = id;
        this.groupId = groupId;
        this.group = group;
        this.delete = delete;
        this.userCode = userCode;
        this.user = user;
        this.title = title;
        this.content = content;
        this.hit = hit;
    }
}

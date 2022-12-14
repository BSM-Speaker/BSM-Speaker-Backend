package bsm.speaker.domain.post.domain;

import bsm.speaker.domain.user.domain.User;
import bsm.speaker.domain.user.domain.dto.response.UserResponse;
import bsm.speaker.domain.user.facade.UserFacade;
import bsm.speaker.global.entity.BaseTimeEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PostViewer extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(columnDefinition = "INT UNSIGNED")
    private long id;

    @ManyToOne
    private Post post;

    @OneToOne
    private User user;

    @Builder
    public PostViewer(long id, Post post, User user) {
        this.id = id;
        this.post = post;
        this.user = user;
    }

    public UserResponse toResponse(UserFacade userFacade) {
        return userFacade.toBoardUserResponse(user);
    }
}

package bsm.speaker.domain.post.domain;

import bsm.speaker.global.entity.BaseTimeEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PostImage extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(columnDefinition = "INT UNSIGNED")
    private long id;

    @ManyToOne
    private Post post;

    private String path;

    @Builder
    public PostImage(long id, Post post, String path) {
        this.id = id;
        this.post = post;
        this.path = path;
    }
}

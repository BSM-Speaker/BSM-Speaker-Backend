package bsm.speaker.domain.post.domain;

import bsm.speaker.domain.group.domain.Group;
import bsm.speaker.domain.post.domain.dto.response.PostResponseDto;
import bsm.speaker.domain.user.domain.User;
import bsm.speaker.domain.user.facade.UserFacade;
import bsm.speaker.global.entity.BaseTimeEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.*;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Post extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(columnDefinition = "INT UNSIGNED")
    private long id;

    @ManyToOne
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

    @OneToMany(mappedBy = "post", fetch = FetchType.EAGER, cascade = CascadeType.REMOVE)
    @OrderBy("id desc")
    private final Set<PostViewer> viewers = new HashSet<>();

    @OneToMany(mappedBy = "post", fetch = FetchType.EAGER, cascade = CascadeType.REMOVE)
    @OrderBy("id desc")
    private Set<PostImage> images = new HashSet<>();

    @Builder
    public Post(long id, String groupId, Group group, Long userCode, User user, String title, String content) {
        this.id = id;
        this.group = group;
        this.userCode = userCode;
        this.user = user;
        this.title = title;
        this.content = content;
    }

    public PostResponseDto toResponse(UserFacade userFacade, User viewUser) {
        return PostResponseDto.builder()
                .groupId(group.getId())
                .id(id)
                .title(title)
                .content(content)
                .user(userFacade.toBoardUserResponse(user))
                .createdAt(getCreatedAt())
                .permission(Objects.equals(viewUser.getUserCode(), user.getUserCode()))
                .viewers(viewers.stream()
                        .map(viewer -> viewer.toResponse(userFacade))
                        .toList()
                )
                .images(images.stream()
                        .map(PostImage::getPath)
                        .toList()
                )
                .build();
    }

    public void setImages(Set<PostImage> images) {
        this.images = images;
    }
}

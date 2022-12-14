package bsm.speaker.domain.group.domain;

import bsm.speaker.global.entity.BaseTimeEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "`group`")
public class Group extends BaseTimeEntity {

    @Id
    @Column(length = 6)
    private String id;

    @Column(length = 32)
    private String name;

    @Column
    private String description;

    @OneToMany(mappedBy = "group", fetch = FetchType.EAGER, cascade = CascadeType.REMOVE)
    private final List<Member> members = new ArrayList<>();

    @Builder
    public Group(String id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
    }
}

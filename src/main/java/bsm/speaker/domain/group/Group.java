package bsm.speaker.domain.group;

import bsm.speaker.global.entity.BaseTimeEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

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

    @Builder
    public Group(String id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
    }
}

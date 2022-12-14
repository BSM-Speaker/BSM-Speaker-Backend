package bsm.speaker.domain.post.domain.dto.response;

import bsm.speaker.domain.user.domain.dto.response.UserResponse;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Builder
public class PostResponseDto {

    private String groupId;

    private long id;

    private String title;

    private String content;

    private UserResponse user;

    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    private LocalDateTime createdAt;

    private boolean permission;

    private List<UserResponse> viewers;
}

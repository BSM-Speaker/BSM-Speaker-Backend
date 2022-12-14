package bsm.speaker.domain.post.domain.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@AllArgsConstructor
public class PostViewRequestDto {

    private long postId;

    @NotBlank
    @Size(min = 6, max = 6)
    private String groupId;

}

package bsm.speaker.domain.post.domain.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@RequiredArgsConstructor
@AllArgsConstructor
public class PostListRequestDto {

    private int page;
    private int limit;

    @NotBlank
    @Size(min = 6, max = 6)
    private String groupId;

}

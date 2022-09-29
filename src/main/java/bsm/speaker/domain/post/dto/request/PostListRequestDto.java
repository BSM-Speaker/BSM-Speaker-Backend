package bsm.speaker.domain.post.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
@AllArgsConstructor
public class PostListRequestDto {

    private int page;
    private int limit;
    private String groupId;
}

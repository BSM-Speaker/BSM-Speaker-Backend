package bsm.speaker.domain.post.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
@AllArgsConstructor
public class PostWriteRequestDto {

    private String title;
    private String content;
    private String groupId;
}

package bsm.speaker.domain.post;

import bsm.speaker.domain.post.dto.request.PostWriteRequestDto;
import bsm.speaker.global.utils.UserUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("post")
public class PostController {

    private final PostService postService;
    private final UserUtil userUtil;

    @PostMapping
    public void writePost(@RequestBody PostWriteRequestDto dto) {
        postService.writePost(userUtil.getCurrentUser(), dto);
    }
}

package bsm.speaker.domain.post;

import bsm.speaker.domain.post.dto.request.PostWriteRequestDto;
import bsm.speaker.domain.post.dto.response.PostResponseDto;
import bsm.speaker.global.utils.UserUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("post")
public class PostController {

    private final PostService postService;
    private final UserUtil userUtil;

    @GetMapping("{groupId}")
    public List<PostResponseDto> postList(@PathVariable("groupId") String groupId) {
        return postService.postList(groupId);
    }

    @PostMapping
    public void writePost(@RequestBody PostWriteRequestDto dto) {
        postService.writePost(userUtil.getCurrentUser(), dto);
    }

    @DeleteMapping("{postId}")
    public void deletePost(@PathVariable("postId") long postId) {
        postService.deletePost(userUtil.getCurrentUser(), postId);
    }
}

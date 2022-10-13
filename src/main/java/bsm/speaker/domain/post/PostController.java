package bsm.speaker.domain.post;

import bsm.speaker.domain.post.dto.request.PostListRequestDto;
import bsm.speaker.domain.post.dto.request.PostWriteRequestDto;
import bsm.speaker.domain.post.dto.response.PostResponseDto;
import bsm.speaker.global.utils.UserUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("post")
public class PostController {

    private final PostService postService;
    private final UserUtil userUtil;

    @GetMapping
    public List<PostResponseDto> postList(
            @RequestParam(value = "page", defaultValue = "1") int page,
            @RequestParam(value = "limit", defaultValue = "15") int limit,
            @RequestParam(value = "groupId", defaultValue = "") String groupId
    ) {
        return postService.postList(new PostListRequestDto(page, limit, groupId));
    }

    @PostMapping
    public void writePost(@RequestBody @Valid PostWriteRequestDto dto) throws JsonProcessingException {
        postService.writePost(userUtil.getCurrentUser(), dto);
    }

    @DeleteMapping("{postId}")
    public void deletePost(@PathVariable("postId") long postId) {
        postService.deletePost(userUtil.getCurrentUser(), postId);
    }
}

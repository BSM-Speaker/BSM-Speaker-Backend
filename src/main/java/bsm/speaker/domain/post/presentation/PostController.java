package bsm.speaker.domain.post.presentation;

import bsm.speaker.domain.post.domain.dto.request.PostListRequestDto;
import bsm.speaker.domain.post.domain.dto.request.PostViewRequestDto;
import bsm.speaker.domain.post.domain.dto.request.PostWriteRequestDto;
import bsm.speaker.domain.post.domain.dto.response.PostResponseDto;
import bsm.speaker.domain.post.service.PostService;
import bsm.speaker.global.utils.UserUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
import java.util.ArrayList;
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
        return postService.postList(new PostListRequestDto(page, limit, groupId), userUtil.getCurrentUser());
    }

    @PostMapping
    public void writePost(
            @RequestPart(value = "groupId") String groupId,
            @RequestPart(value = "title") String title,
            @RequestPart(value = "content") String content,
            @RequestPart(value = "imageList", required = false) List<MultipartFile> imageList
    ) throws JsonProcessingException {
        if (imageList == null) imageList = new ArrayList<>();
        postService.writePost(
                userUtil.getCurrentUser(),
                new PostWriteRequestDto(title, content, groupId, imageList)
        );
    }

    @DeleteMapping("{postId}")
    public void deletePost(@PathVariable("postId") long postId) {
        postService.deletePost(userUtil.getCurrentUser(), postId);
    }

    @GetMapping("view/{groupId}/{postId}")
    public void deletePost(
            @PathVariable("groupId") String groupId,
            @PathVariable("postId") long postId
    ) {
        postService.viewPost(userUtil.getCurrentUser(), new PostViewRequestDto(postId, groupId));
    }

}

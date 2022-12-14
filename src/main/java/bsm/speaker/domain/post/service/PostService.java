package bsm.speaker.domain.post.service;

import bsm.speaker.domain.group.domain.Group;
import bsm.speaker.domain.group.facade.GroupFacade;
import bsm.speaker.domain.post.domain.*;
import bsm.speaker.domain.post.domain.dto.request.PostListRequestDto;
import bsm.speaker.domain.post.domain.dto.request.PostViewRequestDto;
import bsm.speaker.domain.post.domain.dto.request.PostWriteRequestDto;
import bsm.speaker.domain.post.domain.dto.response.PostResponseDto;
import bsm.speaker.domain.post.facade.PostFacade;
import bsm.speaker.domain.user.domain.User;
import bsm.speaker.domain.user.facade.UserFacade;
import bsm.speaker.global.error.exceptions.ForbiddenException;
import bsm.speaker.global.error.exceptions.InternalServerException;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.SecureRandom;
import java.util.Comparator;
import java.util.HexFormat;
import java.util.List;
import java.util.Objects;

@Service
@Validated
@RequiredArgsConstructor
public class PostService {

    private final PostFacade postFacade;
    private final GroupFacade groupFacade;
    private final UserFacade userFacade;
    private final PostNotificationService postNotification;
    private final PostViewerRepository postViewerRepository;
    private final PostImageRepository postImageRepository;

    @Value("${env.file.path.base}")
    private String PUBLIC_RESOURCE_PATH;
    @Value("${env.file.path.upload.image}")
    private String IMAGE_UPLOAD_PATH;

    public List<PostResponseDto> postList(@Valid PostListRequestDto dto, User user) {
        Group group = groupFacade.getGroup(user, dto.getGroupId());
        Pageable pageable = PageRequest.of(dto.getPage() - 1, dto.getLimit());
        return postFacade.getPostList(group, pageable).stream()
                .map(post -> post.toResponse(userFacade, post.getUser()))
                .toList();
    }

    @Transactional
    public void writePost(User user, @Valid PostWriteRequestDto dto) throws JsonProcessingException {
        Group group = groupFacade.getGroup(user, dto.getGroupId());
        List<String> imageList = saveImages(dto.getImageList());

        Post newPost = Post.builder()
                .title(dto.getTitle())
                .content(dto.getContent())
                .userCode(user.getUserCode())
                .group(group)
                .build();
        Post post = postFacade.save(newPost);
        List<PostImage> postImageList = imageList.stream()
                .map(path -> PostImage.builder()
                        .post(post)
                        .path(path)
                        .build()
                ).toList();
        postImageRepository.saveAll(postImageList);

        postNotification.sendNewPostNotification(post);
    }

    public void deletePost(User user, long postId) {
        Post post = postFacade.findById(postId);
        if (!Objects.equals(post.getUserCode(), user.getUserCode())) {
            throw new ForbiddenException("권한이 없습니다");
        }
        postFacade.delete(post);
    }

    public void viewPost(User user, @Valid PostViewRequestDto dto) {
        Group group = groupFacade.getGroup(user, dto.getGroupId());
        Post post = postFacade.findByIdAndGroup(dto.getPostId(), group);
        PostViewer postViewer = PostViewer.builder()
                .post(post)
                .user(user)
                .build();
        postViewerRepository.save(postViewer);
    }

    private List<String> saveImages(List<MultipartFile> files) {
        File dir = new File(PUBLIC_RESOURCE_PATH + IMAGE_UPLOAD_PATH);
        if (!dir.exists()) {
            dir.mkdirs();
        }

        return files.stream().map(file -> {
            String fileExt = Objects.requireNonNull(file.getOriginalFilename()).substring(file.getOriginalFilename().lastIndexOf(".")+1);
            String fileId = getRandomStr(10);
            try {
                file.transferTo(new File(dir.getPath() + "/" + fileId + "." + fileExt));
            } catch (IOException e) {
                e.printStackTrace();
                throw new InternalServerException();
            }
            return fileId + "." + fileExt;
        }).toList();
    }

    private String getRandomStr(int length) {
        SecureRandom secureRandom = new SecureRandom();
        byte[] randomBytes = new byte[length / 2];
        secureRandom.nextBytes(randomBytes);
        return HexFormat.of().formatHex(randomBytes);
    }

}

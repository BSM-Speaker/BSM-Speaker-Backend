package bsm.speaker.domain.post.domain.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.List;

@Getter
@AllArgsConstructor
public class PostWriteRequestDto {

    @NotBlank
    @Size(max = 50)
    private String title;

    @NotBlank
    @Size(max = 100000)
    private String content;

    @NotBlank
    @Size(min = 6, max = 6)
    private String groupId;

    @Size(max = 5)
    private List<MultipartFile> imageList;
}

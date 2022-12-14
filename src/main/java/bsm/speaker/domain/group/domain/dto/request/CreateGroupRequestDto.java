package bsm.speaker.domain.group.domain.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@RequiredArgsConstructor
@AllArgsConstructor
public class CreateGroupRequestDto {

    @NotBlank
    @Size(max = 32)
    private String name;

    @NotBlank
    @Size(max = 255)
    private String description;
}

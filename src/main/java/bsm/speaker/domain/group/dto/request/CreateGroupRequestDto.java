package bsm.speaker.domain.group.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
@AllArgsConstructor
public class CreateGroupRequestDto {

    private String name;
    private String description;
}

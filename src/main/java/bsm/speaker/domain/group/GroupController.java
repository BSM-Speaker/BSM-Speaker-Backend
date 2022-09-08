package bsm.speaker.domain.group;

import bsm.speaker.domain.group.dto.request.CreateGroupRequestDto;
import bsm.speaker.domain.group.dto.response.CreateGroupResponseDto;
import bsm.speaker.global.utils.UserUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("group")
@RequiredArgsConstructor
public class GroupController {

    private final GroupService groupService;
    private final UserUtil userUtil;

    @PostMapping
    public CreateGroupResponseDto createGroup(@RequestBody CreateGroupRequestDto dto) {
        return groupService.createGroup(userUtil.getCurrentUser(), dto);
    }
}

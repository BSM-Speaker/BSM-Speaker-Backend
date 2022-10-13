package bsm.speaker.domain.group;

import bsm.speaker.domain.group.dto.request.CreateGroupRequestDto;
import bsm.speaker.domain.group.dto.request.GroupRequestDto;
import bsm.speaker.domain.group.dto.response.CreateGroupResponseDto;
import bsm.speaker.domain.group.dto.response.GroupResponseDto;
import bsm.speaker.global.utils.UserUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("group")
@RequiredArgsConstructor
public class GroupController {

    private final GroupService groupService;
    private final UserUtil userUtil;

    @GetMapping
    public List<GroupResponseDto> getGroupList() {
        return groupService.getGroupList(userUtil.getCurrentUser());
    }

    @PostMapping
    public CreateGroupResponseDto createGroup(@Valid @RequestBody CreateGroupRequestDto dto) {
        return groupService.createGroup(userUtil.getCurrentUser(), dto);
    }

    @PostMapping("join")
    public void joinGroup(@RequestBody GroupRequestDto dto) {
        groupService.joinGroup(userUtil.getCurrentUser(), dto);
    }
}

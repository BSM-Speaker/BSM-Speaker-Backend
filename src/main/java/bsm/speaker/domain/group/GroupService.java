package bsm.speaker.domain.group;

import bsm.speaker.domain.group.dto.request.CreateGroupRequestDto;
import bsm.speaker.domain.group.dto.response.CreateGroupResponseDto;
import bsm.speaker.domain.user.entities.User;
import bsm.speaker.global.exceptions.ConflictException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.HexFormat;

@Service
@RequiredArgsConstructor
public class GroupService {

    private final GroupRepository groupRepository;

    public CreateGroupResponseDto createGroup(User user, CreateGroupRequestDto dto) {
        if (groupRepository.findByName(dto.getName()).isPresent()) {
            throw new ConflictException("그룹 이름이 이미 존재합니다");
        }

        String groupId = getRandomStr(6);
        groupRepository.save(
                Group.builder()
                        .id(groupId)
                        .name(dto.getName())
                        .description(dto.getDescription())
                        .build()
        );

        return CreateGroupResponseDto.builder()
                .id(groupId)
                .build();
    }

    private String getRandomStr(int length) {
        SecureRandom secureRandom = new SecureRandom();
        byte[] randomBytes = new byte[length / 2];
        secureRandom.nextBytes(randomBytes);
        return HexFormat.of().formatHex(randomBytes);
    }
}

package bsm.speaker.domain.group;

import bsm.speaker.domain.group.dto.request.CreateGroupRequestDto;
import bsm.speaker.domain.group.dto.request.GroupRequestDto;
import bsm.speaker.domain.group.dto.response.CreateGroupResponseDto;
import bsm.speaker.domain.group.dto.response.GroupResponseDto;
import bsm.speaker.domain.group.entities.Group;
import bsm.speaker.domain.group.entities.Member;
import bsm.speaker.domain.group.entities.MemberPk;
import bsm.speaker.domain.group.repositories.GroupRepository;
import bsm.speaker.domain.group.repositories.MemberRepository;
import bsm.speaker.domain.user.dto.response.UserResponseDto;
import bsm.speaker.domain.user.entities.User;
import bsm.speaker.global.error.exceptions.ConflictException;
import bsm.speaker.global.error.exceptions.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.HexFormat;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class GroupService {

    private final GroupRepository groupRepository;
    private final MemberRepository memberRepository;

    public List<GroupResponseDto> getGroupList(User user) {
        return memberRepository.findAllByUser(user).stream().map(member -> {
            Group group = member.getGroup();
            return GroupResponseDto.builder()
                    .id(group.getId())
                    .name(group.getName())
                    .description(group.getDescription())
                    .members(group.getMembers().stream()
                            .map(groupMember -> UserResponseDto.builder()
                                    .code(groupMember.getUser().getUserCode())
                                    .nickname(groupMember.getUser().getNickname())
                                    .build()
                            ).collect(Collectors.toList()))
                    .build();
        }).collect(Collectors.toList());
    }

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
        Member member = Member.builder()
                .pk(
                        MemberPk.builder()
                                .groupId(groupId)
                                .userCode(user.getUserCode())
                                .build()
                )
                .build();
        memberRepository.save(member);

        return CreateGroupResponseDto.builder()
                .id(groupId)
                .build();
    }

    public void joinGroup(User user, GroupRequestDto dto) {
        groupRepository.findById(dto.getGroupId()).orElseThrow(
                () -> {throw new NotFoundException("Group not found");}
        );
        Member member = Member.builder()
                .pk(
                        MemberPk.builder()
                                .groupId(dto.getGroupId())
                                .userCode(user.getUserCode())
                                .build()
                )
                .build();
        memberRepository.save(member);
    }

    private String getRandomStr(int length) {
        SecureRandom secureRandom = new SecureRandom();
        byte[] randomBytes = new byte[length / 2];
        secureRandom.nextBytes(randomBytes);
        return HexFormat.of().formatHex(randomBytes);
    }
}

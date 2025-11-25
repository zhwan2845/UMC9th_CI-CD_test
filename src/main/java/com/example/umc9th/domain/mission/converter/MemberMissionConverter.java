package com.example.umc9th.domain.mission.converter;

import com.example.umc9th.domain.mission.dto.MemberMissionResponseDTO;
import com.example.umc9th.domain.mission.entity.MemberMission;
import org.springframework.data.domain.Page;

public class MemberMissionConverter {

    public static MemberMissionResponseDTO.ChallengeMissionDTO toChallengeMissionDTO(
            MemberMission memberMission
    ) {
        return MemberMissionResponseDTO.ChallengeMissionDTO.builder()
                .missionId(memberMission.getMission().getId())
                .storeName(memberMission.getMission().getStore().getName())
                .status(memberMission.getStatus())
                .content(memberMission.getMission().getContent())
                .deadline(memberMission.getMission().getDeadline())
                .rewardPoint(memberMission.getMission().getRewardPoint())
                .build();
    }

    public static MemberMissionResponseDTO.ChallengeMissionListDTO toChallengeMissionListDTO(
            Page<MemberMission> page
    ) {
        return MemberMissionResponseDTO.ChallengeMissionListDTO.builder()
                .missionList(
                        page.getContent().stream()
                                .map(MemberMissionConverter::toChallengeMissionDTO)
                                .toList()
                )
                .listSize(page.getSize())
                .totalPage(page.getTotalPages())
                .totalElements(page.getTotalElements())
                .isFirst(page.isFirst())
                .isLast(page.isLast())
                .build();
    }
}
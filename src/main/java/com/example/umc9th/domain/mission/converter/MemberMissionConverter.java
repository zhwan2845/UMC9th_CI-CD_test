package com.example.umc9th.domain.mission.converter;

import com.example.umc9th.domain.mission.dto.MemberMissionResponseDTO;
import com.example.umc9th.domain.mission.entity.MemberMission;

public class MemberMissionConverter {

    public static MemberMissionResponseDTO.ChallengeMissionDTO toChallengeMissionDTO(MemberMission memberMission) {
        return MemberMissionResponseDTO.ChallengeMissionDTO.builder()
                .memberMissionId(memberMission.getId())
                .memberId(memberMission.getMember().getId())
                .missionId(memberMission.getMission().getId())
                .status(memberMission.getStatus())
                .createdAt(memberMission.getCreatedAt())
                .build();
    }
}

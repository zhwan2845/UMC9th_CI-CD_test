package com.example.umc9th.domain.mission.dto;

import com.example.umc9th.domain.mission.enums.MissionStatus;
import lombok.Builder;

import java.time.LocalDateTime;

public class MemberMissionResponseDTO {

    @Builder
    public record ChallengeMissionDTO(
            Long memberMissionId,
            Long memberId,
            Long missionId,
            MissionStatus status,
            LocalDateTime createdAt
    ) {}
}

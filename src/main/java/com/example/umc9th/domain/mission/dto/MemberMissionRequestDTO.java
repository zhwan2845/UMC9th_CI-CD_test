package com.example.umc9th.domain.mission.dto;

import com.example.umc9th.global.annotation.ExistChallengingMission;
import jakarta.validation.constraints.NotNull;

public class MemberMissionRequestDTO {

    @ExistChallengingMission
    public record ChallengeMissionDTO(

            @NotNull(message = "회원 ID는 필수 값입니다.")
            Long memberId,

            @NotNull(message = "미션 ID는 필수 값입니다.")
            Long missionId
    ) {}
}

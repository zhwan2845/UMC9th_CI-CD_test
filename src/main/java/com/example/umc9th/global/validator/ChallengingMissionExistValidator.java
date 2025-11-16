package com.example.umc9th.global.validator;

import com.example.umc9th.domain.mission.dto.MemberMissionRequestDTO;
import com.example.umc9th.domain.mission.enums.MissionStatus;
import com.example.umc9th.domain.mission.repository.MemberMissionRepository;
import com.example.umc9th.global.annotation.ExistChallengingMission;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ChallengingMissionExistValidator implements
        ConstraintValidator<ExistChallengingMission, MemberMissionRequestDTO.ChallengeMissionDTO> {

    private final MemberMissionRepository memberMissionRepository;

    @Override
    public boolean isValid(MemberMissionRequestDTO.ChallengeMissionDTO value,
                           ConstraintValidatorContext context) {

        if (value == null) {
            return true;
        }

        Long memberId = value.memberId();
        Long missionId = value.missionId();

        // @NotNull 이 처리하게 넘김
        if (memberId == null || missionId == null) {
            return true;
        }

        boolean alreadyChallenging = memberMissionRepository
                .existsByMember_IdAndMission_IdAndStatus(memberId, missionId, MissionStatus.CHALLENGING);

        if (alreadyChallenging) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("이미 도전 중인 미션입니다.")
                    .addPropertyNode("missionId") // missionId 필드에 에러 매핑
                    .addConstraintViolation();
            return false;
        }

        return true;
    }
}
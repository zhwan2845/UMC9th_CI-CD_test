package com.example.umc9th.domain.mission.service;

import com.example.umc9th.domain.mission.converter.MemberMissionConverter;
import com.example.umc9th.domain.mission.dto.MemberMissionResponseDTO;
import com.example.umc9th.domain.mission.entity.MemberMission;
import com.example.umc9th.domain.mission.enums.MissionStatus;
import com.example.umc9th.domain.mission.exception.MissionException;
import com.example.umc9th.domain.mission.exception.code.MissionErrorCode;
import com.example.umc9th.domain.mission.repository.MemberMissionRepository;
import com.example.umc9th.global.apiPayload.code.GeneralErrorCode;
import com.example.umc9th.global.apiPayload.exception.GeneralException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberMissionQueryServiceImpl implements MemberMissionQueryService {

    private final MemberMissionRepository memberMissionRepository;

    @Override
    public MemberMissionResponseDTO.ChallengeMissionListDTO getChallengingMissions(Long memberId, Integer page) {

        if (memberId == null || memberId <= 0) {
            throw new GeneralException(GeneralErrorCode.BAD_REQUEST);
        }

        int pageIndex = page - 1; // 프론트는 1부터, JPA는 0부터
        PageRequest pageRequest = PageRequest.of(pageIndex, 10); // 한 페이지 10개

        Page<MemberMission> missionPage =
                memberMissionRepository.findAllByMember_IdAndStatusOrderByIdDesc(
                        memberId,
                        MissionStatus.CHALLENGING,
                        pageRequest
                );

        if (missionPage.isEmpty()) {
            throw new MissionException(MissionErrorCode.NOT_FOUND);
        }

        return MemberMissionConverter.toChallengeMissionListDTO(missionPage);
    }
}
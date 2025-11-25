package com.example.umc9th.domain.mission.service;

import com.example.umc9th.domain.mission.dto.MemberMissionResponseDTO;

public interface MemberMissionQueryService {

    MemberMissionResponseDTO.ChallengeMissionListDTO getChallengingMissions(Long memberId, Integer page);
}
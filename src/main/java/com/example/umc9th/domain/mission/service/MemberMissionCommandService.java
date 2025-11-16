package com.example.umc9th.domain.mission.service;

import com.example.umc9th.domain.mission.dto.MemberMissionRequestDTO;
import com.example.umc9th.domain.mission.dto.MemberMissionResponseDTO;

public interface MemberMissionCommandService {

    MemberMissionResponseDTO.ChallengeMissionDTO challengeMission(MemberMissionRequestDTO.ChallengeMissionDTO request);
}

package com.example.umc9th.domain.mission.service;

import com.example.umc9th.domain.mission.dto.MissionResponseDTO;

public interface MissionQueryService {

    MissionResponseDTO.MissionPreviewListDTO getStoreMissions(Long storeId, Integer page);
}

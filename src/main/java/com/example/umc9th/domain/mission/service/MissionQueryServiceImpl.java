package com.example.umc9th.domain.mission.service;

import com.example.umc9th.domain.mission.converter.MissionConverter;
import com.example.umc9th.domain.mission.dto.MissionResponseDTO;
import com.example.umc9th.domain.mission.entity.Mission;
import com.example.umc9th.domain.mission.exception.MissionException;
import com.example.umc9th.domain.mission.exception.code.MissionErrorCode;
import com.example.umc9th.domain.mission.repository.MissionRepository;
import com.example.umc9th.domain.store.entity.Store;
import com.example.umc9th.domain.store.exception.StoreException;
import com.example.umc9th.domain.store.exception.code.StoreErrorCode;
import com.example.umc9th.domain.store.repository.StoreRepository;
import com.example.umc9th.global.apiPayload.code.GeneralErrorCode;
import com.example.umc9th.global.apiPayload.exception.GeneralException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MissionQueryServiceImpl implements MissionQueryService {

    private final MissionRepository missionRepository;
    private final StoreRepository storeRepository;

    @Override
    public MissionResponseDTO.MissionPreviewListDTO getStoreMissions(Long storeId, Integer page) {

        if (storeId == null || storeId <= 0) {
            throw new GeneralException(GeneralErrorCode.BAD_REQUEST);
        }

        Store store = storeRepository.findById(storeId)
                .orElseThrow(() -> new StoreException(StoreErrorCode.NOT_FOUND));

        int pageIndex = page - 1; // 프론트는 1부터, JPA는 0부터
        PageRequest pageRequest = PageRequest.of(pageIndex, 10); // 한 페이지 10개

        Page<Mission> missionPage = missionRepository.findAllByStore(store, pageRequest);

        if (missionPage.isEmpty()) {
            throw new MissionException(MissionErrorCode.NOT_FOUND);
        }

        return MissionConverter.toMissionPreviewListDTO(missionPage);
    }
}
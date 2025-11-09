package com.example.umc9th.domain.store.service;

import com.example.umc9th.domain.store.dto.StoreResponseDTO;
import com.example.umc9th.domain.store.repository.StoreRepository;
import com.example.umc9th.global.apiPayload.code.GeneralErrorCode;
import com.example.umc9th.global.apiPayload.exception.GeneralException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StoreQueryServiceImpl implements StoreQueryService {

    private final StoreRepository storeRepository;

    @Override
    public StoreResponseDTO.SearchPage searchStores(String region, String keyword, String sort, Integer page, Integer size) {
        int p = (page == null || page < 0) ? 0 : page;
        int s = (size == null || size <= 0) ? 10 : size;

        // 유효하지 않은 정렬 조건
        if (sort != null && !sort.equals("latest") && !sort.equals("name")) {
            throw new GeneralException(GeneralErrorCode.BAD_REQUEST);
        }

        List<StoreResponseDTO.SearchItem> fetched =
                storeRepository.searchStores(region, keyword, sort, p, s);

        boolean hasNext = fetched.size() > s;
        List<StoreResponseDTO.SearchItem> content = hasNext ? fetched.subList(0, s) : fetched;

        return StoreResponseDTO.SearchPage.builder()
                .content(content)
                .page(p)
                .size(s)
                .hasNext(hasNext)
                .build();
    }
}

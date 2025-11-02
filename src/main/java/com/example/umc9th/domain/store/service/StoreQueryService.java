package com.example.umc9th.domain.store.service;

import com.example.umc9th.domain.store.dto.StoreResponseDTO;
import com.example.umc9th.domain.store.repository.StoreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StoreQueryService {

    private final StoreRepository storeRepository;

    // 검색 API 서비스. size+1 로 조회된 결과를 잘라 SearchPage로 조립한다.
    public StoreResponseDTO.SearchPage searchStores(String region, String keyword, String sort, Integer page, Integer size) {
        int p = (page == null || page < 0) ? 0 : page;
        int s = (size == null || size <= 0) ? 10 : size;

        List<StoreResponseDTO.SearchItem> fetched = storeRepository.searchStores(region, keyword, sort, p, s);
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

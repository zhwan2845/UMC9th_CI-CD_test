package com.example.umc9th.domain.store.repository;

import com.example.umc9th.domain.store.dto.StoreResponseDTO;

import java.util.List;

public interface StoreQueryDsl {

    /**
     * 지역/키워드/정렬/페이지 기반 스토어 검색
     * @param region  Region.name 부분일치 필터 (nullable)
     * @param keyword 이름 검색. 공백 포함 시 각 단어 OR, 공백 없으면 전체 contains (nullable)
     * @param sort    latest | name
     * @param page    0-base page
     * @param size    page size
     * @return size+1로 hasNext 판단을 위한 결과 리스트(최대 size+1개)
     */
    List<StoreResponseDTO.SearchItem> searchStores(String region, String keyword, String sort, int page, int size);
}

package com.example.umc9th.domain.store.service;

import com.example.umc9th.domain.store.dto.StoreResponseDTO;

public interface StoreQueryService {

    StoreResponseDTO.SearchPage searchStores(String region, String keyword, String sort, Integer page, Integer size);
}
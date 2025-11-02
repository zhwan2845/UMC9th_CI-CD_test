package com.example.umc9th.domain.store.dto;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

public class StoreResponseDTO {

    @Getter
    @Builder
    public static class SearchItem {
        private Long storeId;
        private String name;
        private String address;
        private String regionName;
    }

    @Getter
    @Builder
    public static class SearchPage {
        private List<SearchItem> content;
        private int page;
        private int size;
        private boolean hasNext;
    }
}

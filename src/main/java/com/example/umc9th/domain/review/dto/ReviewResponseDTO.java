package com.example.umc9th.domain.review.dto;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

public class ReviewResponseDTO {

    @Getter
    @Builder
    public static class MyReviewItem {
        private Long reviewId;
        private Long storeId;
        private String storeName;
        private Integer rating;
        private String content;
        private String imageUrl;
        private LocalDateTime createdAt;
    }
}

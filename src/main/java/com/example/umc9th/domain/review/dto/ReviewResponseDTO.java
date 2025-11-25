package com.example.umc9th.domain.review.dto;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

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

    @Getter
    @Builder
    public static class MyReviewListDTO {
        private List<MyReviewItem> reviewList;
        private int listSize;
        private int totalPage;
        private long totalElements;
        private boolean isFirst;
        private boolean isLast;
    }

    @Builder
    public record CreateReviewResult(
            Long reviewId,
            Long storeId,
            Long memberId,
            Integer rating,
            String content,
            String imageUrl,
            LocalDateTime createdAt
    ) {}

    @Builder
    public record ReviewPreViewListDTO(
            List<ReviewPreViewDTO> reviewList,
            Integer listSize,
            Integer totalPage,
            Long totalElements,
            Boolean isFirst,
            Boolean isLast
    ){}

    @Builder
    public record ReviewPreViewDTO(
            String ownerNickname,
            Integer score,
            String body,
            LocalDate createdAt
    ){}
}

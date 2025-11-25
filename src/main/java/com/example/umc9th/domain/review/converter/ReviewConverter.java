package com.example.umc9th.domain.review.converter;

import com.example.umc9th.domain.review.dto.ReviewResponseDTO;
import com.example.umc9th.domain.review.entity.Review;
import com.example.umc9th.domain.review.entity.ReviewImage;
import org.springframework.data.domain.Page;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

public class ReviewConverter {

    public static ReviewResponseDTO.MyReviewItem toMyReviewItem(Review review) {
        return ReviewResponseDTO.MyReviewItem.builder()
                .reviewId(review.getId())
                .storeId(review.getStore().getId())
                .storeName(review.getStore().getName())
                .rating(review.getRating())
                .content(review.getContent())
                .imageUrl(extractImageUrl(review.getReviewImage()))
                .createdAt(review.getCreatedAt())
                .build();
    }

    public static List<ReviewResponseDTO.MyReviewItem> toMyReviewItemList(List<Review> reviews) {
        return reviews.stream()
                .map(ReviewConverter::toMyReviewItem)
                .collect(Collectors.toList());
    }

    public static ReviewResponseDTO.CreateReviewResult toCreateReviewResult(Review review) {
        return ReviewResponseDTO.CreateReviewResult.builder()
                .reviewId(review.getId())
                .storeId(review.getStore().getId())
                .memberId(review.getMember().getId())
                .rating(review.getRating())
                .content(review.getContent())
                .imageUrl(extractImageUrl(review.getReviewImage()))
                .createdAt(review.getCreatedAt())
                .build();
    }

    private static String extractImageUrl(ReviewImage image) {
        return image == null ? null : image.getImageUrl();
    }

    // result -> DTO
    public static ReviewResponseDTO.ReviewPreViewListDTO toReviewPreviewListDTO(
            Page<Review> result
    ){
        return ReviewResponseDTO.ReviewPreViewListDTO.builder()
                .reviewList(result.getContent().stream()
                        .map(ReviewConverter::toReviewPreviewDTO)
                        .toList()
                )
                .listSize(result.getSize())
                .totalPage(result.getTotalPages())
                .totalElements(result.getTotalElements())
                .isFirst(result.isFirst())
                .isLast(result.isLast())
                .build();
    }

    public static ReviewResponseDTO.ReviewPreViewDTO toReviewPreviewDTO(
            Review review
    ){
        return ReviewResponseDTO.ReviewPreViewDTO.builder()
                .ownerNickname(review.getMember().getName())
                .score(review.getRating())
                .body(review.getContent())
                .createdAt(LocalDate.from(review.getCreatedAt()))
                .build();
    }

    public static ReviewResponseDTO.MyReviewListDTO toMyReviewListDTO(Page<Review> page) {
        return ReviewResponseDTO.MyReviewListDTO.builder()
                .reviewList(page.getContent().stream()
                        .map(ReviewConverter::toMyReviewItem)
                        .toList()
                )
                .listSize(page.getSize())
                .totalPage(page.getTotalPages())
                .totalElements(page.getTotalElements())
                .isFirst(page.isFirst())
                .isLast(page.isLast())
                .build();
    }
}

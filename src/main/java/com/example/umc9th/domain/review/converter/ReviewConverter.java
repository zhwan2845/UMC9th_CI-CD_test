package com.example.umc9th.domain.review.converter;

import com.example.umc9th.domain.review.dto.ReviewResponseDTO;
import com.example.umc9th.domain.review.entity.Review;
import com.example.umc9th.domain.review.entity.ReviewImage;

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

    private static String extractImageUrl(ReviewImage image) {
        return image == null ? null : image.getImageUrl();
    }
}

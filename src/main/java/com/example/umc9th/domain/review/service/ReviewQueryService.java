package com.example.umc9th.domain.review.service;

import com.example.umc9th.domain.review.dto.ReviewResponseDTO;
import com.example.umc9th.domain.review.entity.Review;
import com.example.umc9th.domain.review.entity.ReviewImage;
import com.example.umc9th.domain.review.repository.ReviewRepository;
import com.querydsl.core.BooleanBuilder;
import com.example.umc9th.domain.review.entity.QReview;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReviewQueryService {

    private final ReviewRepository reviewRepository;

    /**
     * 내가 작성한 리뷰 목록 조회
     * @param memberId 필수
     * @param storeName 선택. 가게명 정확 일치
     * @param ratingBand 선택. 5/4/3 등 점수 대역
     */
    public List<ReviewResponseDTO.MyReviewItem> getMyReviews(Long memberId, String storeName, Integer ratingBand) {
        QReview r = QReview.review;

        BooleanBuilder builder = new BooleanBuilder();
        builder.and(r.member.id.eq(memberId));

        if (storeName != null && !storeName.isBlank()) {
            builder.and(r.store.name.eq(storeName));
            // 부분 일치가 필요하면: builder.and(r.store.name.contains(storeName));
        }

        if (ratingBand != null) {
            int from = ratingBand;
            int toExclusive = ratingBand + 1;
            builder.and(r.rating.goe(from).and(r.rating.lt(toExclusive)));
        }

        List<Review> reviews = reviewRepository.searchReview(builder);

        return reviews.stream()
                .map(rv -> ReviewResponseDTO.MyReviewItem.builder()
                        .reviewId(rv.getId())
                        .storeId(rv.getStore().getId())
                        .storeName(rv.getStore().getName())
                        .rating(rv.getRating())
                        .content(rv.getContent())
                        .imageUrl(extractImageUrl(rv.getReviewImage()))
                        .createdAt(rv.getCreatedAt())
                        .build())
                .collect(Collectors.toList());
    }

    private String extractImageUrl(ReviewImage image) {
        return image == null ? null : image.getImageUrl();
    }
}
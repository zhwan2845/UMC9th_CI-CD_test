package com.example.umc9th.domain.review.service;

import com.example.umc9th.domain.review.converter.ReviewConverter;
import com.example.umc9th.domain.review.dto.ReviewResponseDTO;
import com.example.umc9th.domain.review.entity.QReview;
import com.example.umc9th.domain.review.entity.Review;
import com.example.umc9th.domain.review.repository.ReviewRepository;
import com.example.umc9th.global.apiPayload.code.GeneralErrorCode;
import com.example.umc9th.global.apiPayload.exception.GeneralException;
import com.querydsl.core.BooleanBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReviewQueryServiceImpl implements ReviewQueryService {

    private final ReviewRepository reviewRepository;

    @Override
    public List<ReviewResponseDTO.MyReviewItem> getMyReviews(Long memberId, String storeName, Integer ratingBand) {
        // 유효하지 않은 memberId 검사
        if (memberId == null || memberId <= 0) {
            throw new GeneralException(GeneralErrorCode.MEMBER_NOT_FOUND);
        }

        QReview r = QReview.review;
        BooleanBuilder builder = new BooleanBuilder().and(r.member.id.eq(memberId));

        if (storeName != null && !storeName.isBlank()) {
            builder.and(r.store.name.eq(storeName));
        }

        if (ratingBand != null) {
            if (ratingBand < 1 || ratingBand > 5) {
                throw new GeneralException(GeneralErrorCode.BAD_REQUEST);
            }
            builder.and(r.rating.eq(ratingBand));
        }

        List<Review> reviews = reviewRepository.searchReview(builder);

        // 리뷰가 없을 때 (에러로 처리하고 싶을 경우)
        if (reviews.isEmpty()) {
            throw new GeneralException(GeneralErrorCode.NOT_FOUND);
        }

        return ReviewConverter.toMyReviewItemList(reviews);
    }
}
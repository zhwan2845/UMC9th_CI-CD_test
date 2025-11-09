package com.example.umc9th.domain.review.service;

import com.example.umc9th.domain.review.dto.ReviewResponseDTO;

import java.util.List;

public interface ReviewQueryService {

    List<ReviewResponseDTO.MyReviewItem> getMyReviews(Long memberId, String storeName, Integer ratingBand);
}
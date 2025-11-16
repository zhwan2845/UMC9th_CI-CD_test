package com.example.umc9th.domain.review.service;

import com.example.umc9th.domain.review.dto.ReviewRequestDTO;
import com.example.umc9th.domain.review.dto.ReviewResponseDTO;

public interface ReviewCommandService {

    ReviewResponseDTO.CreateReviewResult createReview(Long storeId, ReviewRequestDTO.CreateReviewDTO request);
}

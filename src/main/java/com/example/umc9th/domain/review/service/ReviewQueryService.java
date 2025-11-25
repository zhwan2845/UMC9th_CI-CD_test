package com.example.umc9th.domain.review.service;

import com.example.umc9th.domain.review.dto.ReviewResponseDTO;

import java.util.List;

public interface ReviewQueryService {

    ReviewResponseDTO.ReviewPreViewListDTO findReview(String storeName, Integer page);

    ReviewResponseDTO.MyReviewListDTO getMyReviews(Long memberId, Integer page);

}
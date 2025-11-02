package com.example.umc9th.domain.review.repository;

import com.example.umc9th.domain.review.entity.Review;
import com.querydsl.core.types.Predicate;

import java.util.List;

public interface ReviewQueryDsl {

    // 동적 조건(Predicate)을 받아 리뷰 목록 반환
    List<Review> searchReview(Predicate predicate);
}

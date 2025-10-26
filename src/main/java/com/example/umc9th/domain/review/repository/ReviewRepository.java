package com.example.umc9th.domain.review.repository;

import com.example.umc9th.domain.review.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

public interface ReviewRepository extends JpaRepository<Review, Long> {

    @Transactional
    @Modifying
    @Query(value = """
        INSERT INTO review (member_id, store_id, content, rating, created_at)
        VALUES (:memberId, :storeId, :content, :rating, now())
        """, nativeQuery = true)
    int insertReviewNative(Long memberId, Long storeId, String content, Integer rating);
}

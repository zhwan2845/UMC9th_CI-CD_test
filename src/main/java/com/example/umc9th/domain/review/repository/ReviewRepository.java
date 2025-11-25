package com.example.umc9th.domain.review.repository;

import com.example.umc9th.domain.review.entity.Review;
import com.example.umc9th.domain.store.entity.Store;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

public interface ReviewRepository extends JpaRepository<Review, Long>, ReviewQueryDsl {

    @Transactional
    @Modifying
    @Query(value = """
        INSERT INTO review (member_id, store_id, content, rating, created_at)
        VALUES (:memberId, :storeId, :content, :rating, now())
        """, nativeQuery = true)
    int insertReviewNative(Long memberId, Long storeId, String content, Integer rating);

    Page<Review> findAllByStore(Store store, Pageable pageable);

    Page<Review> findAllByMember_Id(Long memberId, Pageable pageable);
}

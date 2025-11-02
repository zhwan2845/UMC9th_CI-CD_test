package com.example.umc9th.domain.store.repository;

import com.example.umc9th.domain.region.entity.QRegion;
import com.example.umc9th.domain.store.dto.StoreResponseDTO;
import com.example.umc9th.domain.store.entity.QStore;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class StoreQueryDslImpl implements StoreQueryDsl {

    private final EntityManager em;

    @Override
    public List<StoreResponseDTO.SearchItem> searchStores(String region, String keyword, String sort, int page, int size) {
        JPAQueryFactory query = new JPAQueryFactory(em);

        QStore store = QStore.store;
        QRegion rg = QRegion.region;

        BooleanBuilder where = new BooleanBuilder();

        // 지역 필터: Region.name 부분일치
        if (region != null && !region.isBlank()) {
            where.and(rg.name.contains(region));
        }

        // 이름 검색: 공백 포함 → 단어 OR, 공백 없음 → 전체 contains
        if (keyword != null && !keyword.isBlank()) {
            String trimmed = keyword.trim();
            if (trimmed.contains(" ")) {
                BooleanBuilder or = new BooleanBuilder();
                Arrays.stream(trimmed.split("\\s+"))
                        .filter(w -> !w.isBlank())
                        .forEach(w -> or.or(store.name.contains(w)));
                where.and(or);
            } else {
                where.and(store.name.contains(trimmed));
            }
        }

        // 정렬
        List<OrderSpecifier<?>> orders = new ArrayList<>();
        if ("name".equalsIgnoreCase(sort)) {
            // 이름 첫 글자 그룹: 가-힣(1) > A-Z(2) > a-z(3) > 기타(4)
            orders.add(
                    Expressions.numberTemplate(Integer.class,
                            "CASE " +
                                    "WHEN {0} REGEXP '^[가-힣]' THEN 1 " +
                                    "WHEN {0} REGEXP '^[A-Z]' THEN 2 " +
                                    "WHEN {0} REGEXP '^[a-z]' THEN 3 " +
                                    "ELSE 4 END",
                            store.name
                    ).asc()
            );
            orders.add(store.name.asc());       // 이름 오름차순
            orders.add(store.createdAt.desc()); // 동명이점 최신순
        } else {
            // latest 기본
            orders.add(store.createdAt.desc());
        }

        int safeSize = size <= 0 ? 10 : size;
        int safePage = Math.max(0, page);

        return query
                .select(Projections.constructor(
                        StoreResponseDTO.SearchItem.class,
                        store.id,
                        store.name,
                        store.address,
                        rg.name
                ))
                .from(store)
                .join(store.region, rg)
                .where(where)
                .orderBy(orders.toArray(new OrderSpecifier<?>[0]))
                .offset((long) safePage * safeSize)
                .limit(safeSize + 1) // size+1로 hasNext 판단
                .fetch();
    }
}

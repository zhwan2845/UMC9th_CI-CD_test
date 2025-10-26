package com.example.umc9th.domain.mission.repository;

import com.example.umc9th.domain.mission.entity.MemberMission;
import com.example.umc9th.domain.mission.enums.MissionStatus;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MemberMissionRepository extends JpaRepository<MemberMission, Long> {

    // 진행중/완료 상태별로, 특정 회원의 미션 중에서 mission.id < cursor 조건을 만족하는 최신순 DESC, limit(Pageable) 적용
    List<MemberMission> findByMember_IdAndStatusAndMission_IdLessThanOrderByMission_IdDesc(
            Long memberId,
            MissionStatus status,
            Long cursor,
            Pageable pageable
    );

    // 커서가 없을 때(첫 페이지)용: mission.id < cursor 조건 없이 최신순 DESC
    List<MemberMission> findByMember_IdAndStatusOrderByMission_IdDesc(
            Long memberId,
            MissionStatus status,
            Pageable pageable
    );
}

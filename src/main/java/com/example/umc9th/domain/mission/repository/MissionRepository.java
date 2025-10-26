package com.example.umc9th.domain.mission.repository;

import com.example.umc9th.domain.mission.entity.Mission;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MissionRepository extends JpaRepository<Mission, Long> {

    @Query("""
        select m
        from Mission m
        join m.store s
        join s.region r
        where r.name = :regionName
          and m.deadline > current_date
          and (:cursor is null or m.id < :cursor)
          and not exists (
              select 1
              from MemberMission mm
              where mm.mission = m
                and mm.member.id = :memberId
          )
        order by m.id desc
        """)
    List<Mission> findHomeMissionsForRegionExcludingMemberJoined(
            @Param("regionName") String regionName,
            @Param("memberId") Long memberId,
            @Param("cursor") Long cursor,
            Pageable pageable
    );
}

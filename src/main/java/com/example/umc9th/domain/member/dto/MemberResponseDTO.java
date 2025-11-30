package com.example.umc9th.domain.member.dto;

import lombok.Builder;

import java.time.LocalDateTime;

public class MemberResponseDTO {

    @Builder
    public record JoinDTO(
            Long memberId,
            LocalDateTime createAt
    ){}

    // 로그인
    @Builder
    public record LoginDTO(
            Long memberId,
            String accessToken
    ){}
}


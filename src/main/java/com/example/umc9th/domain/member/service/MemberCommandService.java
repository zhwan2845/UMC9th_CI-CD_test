package com.example.umc9th.domain.member.service;

import com.example.umc9th.domain.member.dto.MemberRequestDTO;
import com.example.umc9th.domain.member.dto.MemberResponseDTO;

public interface MemberCommandService {
    // 회원가입
    MemberResponseDTO.JoinDTO signup(
            MemberRequestDTO.JoinDTO dto
    );
}

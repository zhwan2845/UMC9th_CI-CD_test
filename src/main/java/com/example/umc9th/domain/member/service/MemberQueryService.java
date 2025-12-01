package com.example.umc9th.domain.member.service;

import com.example.umc9th.domain.member.dto.MemberRequestDTO;
import com.example.umc9th.domain.member.dto.MemberResponseDTO;

public interface MemberQueryService {
    MemberResponseDTO.LoginDTO login(MemberRequestDTO.LoginDTO dto);
    MemberResponseDTO.LoginDTO reissue(MemberRequestDTO.ReissueDTO dto); // 추가
}

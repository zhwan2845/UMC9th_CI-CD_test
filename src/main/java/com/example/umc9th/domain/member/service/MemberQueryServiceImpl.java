package com.example.umc9th.domain.member.service;

import com.example.umc9th.domain.member.dto.MemberRequestDTO;
import com.example.umc9th.domain.member.dto.MemberResponseDTO;
import com.example.umc9th.domain.member.entity.Member;
import com.example.umc9th.domain.member.exception.MemberException;
import com.example.umc9th.domain.member.exception.code.MemberErrorCode;
import com.example.umc9th.domain.member.repository.MemberRepository;
import com.example.umc9th.domain.member.converter.MemberConverter;
import com.example.umc9th.global.jwt.JwtUtil;
import com.example.umc9th.global.security.CustomUserDetails;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberQueryServiceImpl implements MemberQueryService{

    private final MemberRepository memberRepository;
    private final JwtUtil jwtUtil;
    private final PasswordEncoder encoder;

    @Override
    public MemberResponseDTO.LoginDTO login(
            MemberRequestDTO.@Valid LoginDTO dto
    ) {

        // Member 조회
        Member member = memberRepository.findByEmail(dto.email())
                .orElseThrow(() -> new MemberException(MemberErrorCode.NOT_FOUND));

        // 비밀번호 검증
        if (!encoder.matches(dto.password(), member.getPassword())){
            throw new MemberException(MemberErrorCode.INVALID);
        }

        // JWT 토큰 발급용 UserDetails
        CustomUserDetails userDetails = new CustomUserDetails(member);

        // 엑세스 토큰 발급
        String accessToken = jwtUtil.createAccessToken(userDetails);
        String refreshToken = jwtUtil.createRefreshToken(userDetails);

        // DTO 조립
        return MemberConverter.toLoginDTO(member, accessToken, refreshToken);
    }

    // refresh 토큰 재발급
    @Override
    public MemberResponseDTO.LoginDTO reissue(
            MemberRequestDTO.ReissueDTO dto
    ) {
        String refreshToken = dto.refreshToken();

        // refresh 토큰 검증
        if (!jwtUtil.isValid(refreshToken)) {
            throw new MemberException(MemberErrorCode.INVALID);
        }

        String email = jwtUtil.getEmail(refreshToken);
        if (email == null) {
            throw new MemberException(MemberErrorCode.INVALID);
        }

        Member member = memberRepository.findByEmail(email)
                .orElseThrow(() -> new MemberException(MemberErrorCode.NOT_FOUND));

        CustomUserDetails userDetails = new CustomUserDetails(member);

        // 새로운 access 토큰 발급
        String newAccessToken = jwtUtil.createAccessToken(userDetails);

        // refresh 토큰은 그대로 재사용 (원하면 여기서 새로 발급해도 됨)
        String newRefreshToken = refreshToken;

        return MemberConverter.toLoginDTO(member, newAccessToken, newRefreshToken);
    }
}
package com.example.umc9th.global.kakao;

import com.example.umc9th.domain.member.converter.MemberConverter;
import com.example.umc9th.domain.member.dto.MemberResponseDTO;
import com.example.umc9th.domain.member.entity.Member;
import com.example.umc9th.domain.member.enums.Gender;
import com.example.umc9th.domain.member.repository.MemberRepository;
import com.example.umc9th.global.auth.enums.Role;
import com.example.umc9th.global.jwt.JwtUtil;
import com.example.umc9th.global.security.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class KakaoAuthService {

    private final KakaoOAuthService kakaoOAuthService;
    private final MemberRepository memberRepository;
    private final JwtUtil jwtUtil;
    private final PasswordEncoder passwordEncoder;

    public MemberResponseDTO.LoginDTO loginWithCode(String code) {

        // 1) 인가 코드로 카카오 access token 발급
        String kakaoAccessToken = kakaoOAuthService.getAccessToken(code);

        // 2) access token으로 카카오 유저 정보 조회
        KakaoOAuthService.KakaoUserInfo kakaoUserInfo = kakaoOAuthService.getUserInfo(kakaoAccessToken);

        final String email = (kakaoUserInfo.getKakaoAccount() != null)
                ? kakaoUserInfo.getKakaoAccount().getEmail()
                : null;

        if (email == null) {
            throw new RuntimeException("카카오에서 이메일을 제공하지 않았습니다.");
        }

        // 3) 우리 서비스 Member 조회 또는 신규 생성
        Member member = memberRepository.findByEmail(email)
                .orElseGet(() -> createMemberFromKakao(email));

        // 4) 우리 JWT 발급
        CustomUserDetails userDetails = new CustomUserDetails(member);
        String accessToken = jwtUtil.createAccessToken(userDetails);
        String refreshToken = jwtUtil.createRefreshToken(userDetails);

        return MemberConverter.toLoginDTO(member, accessToken, refreshToken);
    }

    private Member createMemberFromKakao(String email) {
        String encodedPassword = passwordEncoder.encode(UUID.randomUUID().toString());

        Member member = Member.builder()
                .name("카카오사용자")
                .email(email)
                .password(encodedPassword)
                .role(Role.ROLE_USER)
                .gender(Gender.FEMALE) // 임시 기본 설정
                .birth(LocalDate.of(2000, 1, 1)) // 임시 기본 생년월일
                .address("소셜로그인 기본주소")
                .detailAddress("소셜로그인 상세주소")
                .build();

        return memberRepository.save(member);
    }
}
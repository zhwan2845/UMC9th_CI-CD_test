package com.example.umc9th.global.kakao;

import com.example.umc9th.domain.member.dto.MemberResponseDTO;
import com.example.umc9th.domain.member.exception.code.MemberSuccessCode;
import com.example.umc9th.global.apiPayload.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AuthController {

    private final KakaoAuthService kakaoAuthService;

    // 카카오 Redirect URI
    @GetMapping("/oauth")
    public ApiResponse<MemberResponseDTO.LoginDTO> kakaoCallback(
            @RequestParam("code") String code
    ) {
        MemberResponseDTO.LoginDTO result = kakaoAuthService.loginWithCode(code);
        return ApiResponse.onSuccess(MemberSuccessCode.FOUND, result);
    }
}

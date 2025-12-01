package com.example.umc9th.global.kakao;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class KakaoOAuthService {

    @Value("${kakao.rest-api-key}")
    private String restApiKey;

    @Value("${kakao.redirect-uri}")
    private String redirectUri;

    @Value("${kakao.token-uri}")
    private String tokenUri;

    @Value("${kakao.user-info-uri}")
    private String userInfoUri;

    private final RestTemplate restTemplate = new RestTemplate();

    // 1) 인가 코드로 카카오 access token 발급
    public String getAccessToken(String code) {

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        String body = "grant_type=authorization_code"
                + "&client_id=" + restApiKey
                + "&redirect_uri=" + redirectUri
                + "&code=" + code;

        HttpEntity<String> entity = new HttpEntity<>(body, headers);

        ResponseEntity<KakaoTokenResponse> response =
                restTemplate.exchange(tokenUri, HttpMethod.POST, entity, KakaoTokenResponse.class);

        KakaoTokenResponse tokenResponse = response.getBody();
        if (tokenResponse == null || tokenResponse.getAccessToken() == null) {
            throw new RuntimeException("카카오 토큰 발급 실패");
        }
        return tokenResponse.getAccessToken();
    }

    // 2) access token으로 카카오 유저 정보 조회
    public KakaoUserInfo getUserInfo(String accessToken) {

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(accessToken);

        HttpEntity<Void> entity = new HttpEntity<>(headers);

        ResponseEntity<KakaoUserInfo> response =
                restTemplate.exchange(userInfoUri, HttpMethod.GET, entity, KakaoUserInfo.class);

        KakaoUserInfo userInfo = response.getBody();
        if (userInfo == null) {
            throw new RuntimeException("카카오 사용자 정보 조회 실패");
        }
        return userInfo;
    }

    @Getter
    @JsonIgnoreProperties(ignoreUnknown = true)
    private static class KakaoTokenResponse {

        @JsonProperty("access_token")
        private String accessToken;

        @JsonProperty("refresh_token")
        private String refreshToken;
    }

    @Getter
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class KakaoUserInfo {

        private Long id;

        @JsonProperty("kakao_account")
        private KakaoAccount kakaoAccount;

        @Getter
        @JsonIgnoreProperties(ignoreUnknown = true)
        public static class KakaoAccount {

            private String email;
        }
    }
}

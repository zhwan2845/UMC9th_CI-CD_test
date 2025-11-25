package com.example.umc9th.domain.review.exception.code;

import com.example.umc9th.global.apiPayload.code.BaseSuccessCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ReviewSuccessCode implements BaseSuccessCode {

    FOUND(HttpStatus.FOUND,
            "REVIEW200_1",
            "리뷰 정보를 성공적으로 조회하였습니다.");

    private final HttpStatus status;
    private final String code;
    private final String message;
}

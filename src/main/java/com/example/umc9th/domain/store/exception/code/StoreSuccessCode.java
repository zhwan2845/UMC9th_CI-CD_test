package com.example.umc9th.domain.store.exception.code;


import com.example.umc9th.global.apiPayload.code.BaseSuccessCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum StoreSuccessCode implements BaseSuccessCode {

    FOUND(HttpStatus.FOUND,
            "STORE200_1",
            "가게를 성공적으로 조회하였습니다.");

    private final HttpStatus status;
    private final String code;
    private final String message;
}
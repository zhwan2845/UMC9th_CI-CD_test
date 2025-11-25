package com.example.umc9th.domain.mission.exception.code;

import com.example.umc9th.global.apiPayload.code.BaseSuccessCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum MissionSuccessCode implements BaseSuccessCode {

    FOUND(HttpStatus.FOUND,
            "MISSION200_1",
            "미션 정보를 성공적으로 조회하였습니다.");

    private final HttpStatus status;
    private final String code;
    private final String message;
}

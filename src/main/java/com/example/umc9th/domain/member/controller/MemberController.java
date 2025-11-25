package com.example.umc9th.domain.member.controller;

import com.example.umc9th.domain.member.dto.MemberRequestDTO;
import com.example.umc9th.domain.member.dto.MemberResponseDTO;
import com.example.umc9th.domain.member.exception.code.MemberSuccessCode;
import com.example.umc9th.domain.member.service.MemberCommandService;
import com.example.umc9th.domain.mission.dto.MemberMissionResponseDTO;
import com.example.umc9th.domain.mission.exception.code.MissionSuccessCode;
import com.example.umc9th.domain.mission.service.MemberMissionQueryService;
import com.example.umc9th.domain.review.dto.ReviewResponseDTO;
import com.example.umc9th.domain.review.exception.code.ReviewSuccessCode;
import com.example.umc9th.domain.review.service.ReviewQueryService;
import com.example.umc9th.global.annotation.ValidPage;
import com.example.umc9th.global.apiPayload.ApiResponse;
import com.example.umc9th.global.apiPayload.code.GeneralSuccessCode;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@Validated
public class MemberController {

    private final MemberCommandService memberCommandService;
    private final ReviewQueryService reviewQueryService;
    private final MemberMissionQueryService memberMissionQueryService;

    // 회원가입
    @PostMapping("/sign-up")
    public ApiResponse<MemberResponseDTO.JoinDTO> signUp(
            @RequestBody @Valid MemberRequestDTO.JoinDTO dto
    ){
        return ApiResponse.onSuccess(MemberSuccessCode.FOUND, memberCommandService.signup(dto));
    }

    // 내가 작성한 리뷰 목록 조회
    @Operation(
            summary = "내가 작성한 리뷰 목록 조회 API",
            description = "특정 회원이 작성한 리뷰를 페이지 단위로 조회합니다. 한 페이지에 10개씩 조회됩니다."
    )
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "성공"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "잘못된 page 값 또는 요청"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "회원 또는 리뷰 미존재")
    })
    @GetMapping("/members/{memberId}/reviews")
    public ApiResponse<ReviewResponseDTO.MyReviewListDTO> getMemberReviews(
            @PathVariable Long memberId,
            @ValidPage @RequestParam(defaultValue = "1") Integer page
    ) {
        ReviewResponseDTO.MyReviewListDTO result =
                reviewQueryService.getMyReviews(memberId, page);

        return ApiResponse.onSuccess(ReviewSuccessCode.FOUND, result);
    }

    // 내가 진행중인 미션 목록 조회
    @Operation(
            summary = "내가 진행중인 미션 목록 조회 API",
            description = "특정 회원이 진행중인 미션 목록을 페이지 단위로 조회합니다. 한 페이지에 10개씩 조회됩니다."
    )
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "성공"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "잘못된 요청 (page < 1 등)"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "회원 또는 진행중인 미션이 없음")
    })
    @GetMapping("/members/{memberId}/missions/challenging")
    public ApiResponse<MemberMissionResponseDTO.ChallengeMissionListDTO> getChallengingMissions(
            @PathVariable Long memberId,
            @ValidPage @RequestParam(defaultValue = "1") Integer page
    ) {
        MemberMissionResponseDTO.ChallengeMissionListDTO result =
                memberMissionQueryService.getChallengingMissions(memberId, page);

        return ApiResponse.onSuccess(MissionSuccessCode.FOUND, result);
    }
}
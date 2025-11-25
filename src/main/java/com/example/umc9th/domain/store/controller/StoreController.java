package com.example.umc9th.domain.store.controller;

import com.example.umc9th.domain.mission.dto.MissionResponseDTO;
import com.example.umc9th.domain.mission.exception.code.MissionSuccessCode;
import com.example.umc9th.domain.mission.service.MissionQueryService;
import com.example.umc9th.domain.review.dto.ReviewRequestDTO;
import com.example.umc9th.domain.review.dto.ReviewResponseDTO;
import com.example.umc9th.domain.review.service.ReviewCommandService;
import com.example.umc9th.domain.store.dto.StoreResponseDTO;
import com.example.umc9th.domain.store.service.StoreQueryService;
import com.example.umc9th.global.annotation.ValidPage;
import com.example.umc9th.global.apiPayload.ApiResponse;
import com.example.umc9th.global.apiPayload.code.GeneralSuccessCode;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/stores")
public class StoreController {

    private final StoreQueryService storeQueryService;
    private final ReviewCommandService reviewCommandService;
    private final MissionQueryService missionQueryService;

    @GetMapping("/search")
    public ResponseEntity<ApiResponse<StoreResponseDTO.SearchPage>> searchStores(
            @RequestParam(required = false) String region,
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false, defaultValue = "latest") String sort,
            @RequestParam(required = false, defaultValue = "0") Integer page,
            @RequestParam(required = false, defaultValue = "10") Integer size
    ) {
        StoreResponseDTO.SearchPage result =
                storeQueryService.searchStores(region, keyword, sort, page, size);

        return ResponseEntity
                .status(GeneralSuccessCode.OK.getStatus())
                .body(ApiResponse.onSuccess(GeneralSuccessCode.OK, result));
    }

    // 가게에 리뷰 추가 API
    @PostMapping("/{storeId}/reviews")
    public ResponseEntity<ApiResponse<ReviewResponseDTO.CreateReviewResult>> createReview(
            @PathVariable Long storeId,
            @RequestBody @Valid ReviewRequestDTO.CreateReviewDTO request
    ) {
        ReviewResponseDTO.CreateReviewResult result =
                reviewCommandService.createReview(storeId, request);

        return ResponseEntity
                .status(GeneralSuccessCode.OK.getStatus())
                .body(ApiResponse.onSuccess(GeneralSuccessCode.OK, result));
    }

    @Operation(
            summary = "특정 가게의 미션 목록 조회 API",
            description = "storeId에 해당하는 가게의 미션들을 페이지 단위로 조회합니다. 한 페이지에 10개씩 조회됩니다."
    )
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "성공"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "잘못된 요청 (page < 1 등)"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "가게 혹은 미션을 찾을 수 없음")
    })
    @GetMapping("/{storeId}/missions")
    public ApiResponse<MissionResponseDTO.MissionPreviewListDTO> getStoreMissions(
            @PathVariable Long storeId,
            @ValidPage @RequestParam(defaultValue = "1") Integer page
    ) {
        MissionResponseDTO.MissionPreviewListDTO result =
                missionQueryService.getStoreMissions(storeId, page);

        return ApiResponse.onSuccess(MissionSuccessCode.FOUND, result);
    }
}

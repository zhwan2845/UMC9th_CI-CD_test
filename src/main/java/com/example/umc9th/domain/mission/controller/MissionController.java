package com.example.umc9th.domain.mission.controller;

import com.example.umc9th.domain.mission.dto.MemberMissionRequestDTO;
import com.example.umc9th.domain.mission.dto.MemberMissionResponseDTO;
import com.example.umc9th.domain.mission.service.MemberMissionCommandService;
import com.example.umc9th.global.apiPayload.ApiResponse;
import com.example.umc9th.global.apiPayload.code.GeneralSuccessCode;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/missions")
public class MissionController {

    private final MemberMissionCommandService memberMissionCommandService;

    // POST /missions/challenge
    @PostMapping("/challenge")
    public ResponseEntity<ApiResponse<MemberMissionResponseDTO.ChallengeMissionDTO>> challengeMission(
            @RequestBody @Valid MemberMissionRequestDTO.ChallengeMissionDTO request
    ) {
        MemberMissionResponseDTO.ChallengeMissionDTO result =
                memberMissionCommandService.challengeMission(request);

        return ResponseEntity
                .status(GeneralSuccessCode.OK.getStatus())
                .body(ApiResponse.onSuccess(GeneralSuccessCode.OK, result));
    }
}

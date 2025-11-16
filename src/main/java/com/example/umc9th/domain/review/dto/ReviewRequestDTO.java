package com.example.umc9th.domain.review.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class ReviewRequestDTO {

    public record CreateReviewDTO(

            @NotNull(message = "회원 ID는 필수 값입니다.")
            Long memberId,

            @NotNull(message = "별점은 필수 값입니다.")
            @Min(value = 1, message = "별점은 1 이상이어야 합니다.")
            @Max(value = 5, message = "별점은 5 이하여야 합니다.")
            Integer rating,

            @NotBlank(message = "리뷰 내용은 공백일 수 없습니다.")
            String content

            // 이미지 URL을 요청에서 같이 받는다면 여기에 선택 필드로 추가 가능
            // String imageUrl
    ) {}
}

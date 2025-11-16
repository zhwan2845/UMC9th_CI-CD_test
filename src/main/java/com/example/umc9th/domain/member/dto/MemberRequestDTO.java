package com.example.umc9th.domain.member.dto;

import com.example.umc9th.domain.member.enums.Gender;
import com.example.umc9th.global.annotation.ExistFoods;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.util.List;

public class MemberRequestDTO {

    public record JoinDTO(
            @NotBlank
            String name,

            @NotNull
            Gender gender,

            @NotNull
            LocalDate birth,

            @NotNull
            String address,

            @NotNull
            String specAddress,

            @ExistFoods
            List<Long> preferCategory
    ){}
}
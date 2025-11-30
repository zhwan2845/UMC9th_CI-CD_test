package com.example.umc9th.domain.member.dto;

import com.example.umc9th.domain.member.enums.Gender;
import com.example.umc9th.global.annotation.ExistFoods;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.util.List;

public class MemberRequestDTO {

    public record JoinDTO(
            @NotBlank
            String name,

            @Email
            String email,

            @NotBlank
            String password,

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

    // 로그인
    public record LoginDTO(
            @NotBlank
            String email,

            @NotBlank
            String password
    ){}
}
package com.example.umc9th.domain.member.converter;

import com.example.umc9th.domain.member.dto.MemberRequestDTO;
import com.example.umc9th.domain.member.dto.MemberResponseDTO;
import com.example.umc9th.domain.member.entity.Member;
import com.example.umc9th.global.auth.enums.Role;

public class MemberConverter {

    // Entity -> DTO
    public static MemberResponseDTO.JoinDTO toJoinDTO(
            Member member
    ){
        return MemberResponseDTO.JoinDTO.builder()
                .memberId(member.getId())
                .createAt(member.getCreatedAt())
                .build();
    }

    // DTO, Salted Password, Role -> Entity
    public static Member toMember(
            MemberRequestDTO.JoinDTO dto,
            String password,
            Role role
    ){
        return Member.builder()
                .name(dto.name())
                .email(dto.email()) // 추가된 코드
                .password(password) // 추가된 코드
                .role(role)         // 추가된 코드
                .birth(dto.birth())
                .address(dto.address())
                .detailAddress(dto.specAddress())
                .gender(dto.gender())
                .build();
    }

    public static MemberResponseDTO.LoginDTO toLoginDTO(
            Member member,
            String accessToken
    ) {
        return MemberResponseDTO.LoginDTO.builder()
                .memberId(member.getId())
                .accessToken(accessToken)
                .build();
    }
}
package com.example.kpass.service;

import com.example.kpass.entity.dto.MemberRequestDto;
import com.example.kpass.entity.dto.MemberResponseDto;

import java.util.List;
import java.util.UUID;

public interface MemberService {

    MemberResponseDto insertMember(MemberRequestDto memberRequestDto);

    MemberResponseDto updateMember(UUID memberUUID, MemberRequestDto memberRequestDto);

    void deleteMember(String uuidString);

    List<MemberResponseDto> findAllMembers();

    MemberResponseDto findMemberByUUID(String uuidString);
}

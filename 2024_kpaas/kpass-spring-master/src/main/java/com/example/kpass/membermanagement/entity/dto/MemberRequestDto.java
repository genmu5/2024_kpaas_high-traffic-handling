package com.example.kpass.entity.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.UUID;

public class MemberRequestDto {
    private String memberName;
    private String memberEmail;

    public MemberRequestDto(String memberName, String memberEmail) {
        this.memberName = memberName;
        this.memberEmail = memberEmail;
    }

    public String getMemberName() {
        return memberName;
    }

    public String getMemberEmail() {
        return memberEmail;
    }
}

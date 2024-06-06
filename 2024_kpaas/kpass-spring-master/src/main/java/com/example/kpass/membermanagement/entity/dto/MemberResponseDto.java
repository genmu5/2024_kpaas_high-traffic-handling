package com.example.kpass.entity.dto;

import com.example.kpass.entity.Member;

import java.time.LocalDateTime;
import java.util.UUID;

public class MemberResponseDto {
    private UUID memberUUID;
    private String memberName;
    private String memberEmail;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public MemberResponseDto(UUID memberUUID, String memberName, String memberEmail, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.memberUUID = memberUUID;
        this.memberName = memberName;
        this.memberEmail = memberEmail;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public static MemberResponseDto fromEntity(Member member) {
        return new MemberResponseDto(
                member.getMemberUUID(),
                member.getMemberName(),
                member.getMemberEmail(),
                member.getCreatedAt(),
                member.getUpdatedAt());
    }

    public UUID getMemberUUID() {
        return memberUUID;
    }

    public String getMemberName() {
        return memberName;
    }

    public String getMemberEmail() {
        return memberEmail;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }
}

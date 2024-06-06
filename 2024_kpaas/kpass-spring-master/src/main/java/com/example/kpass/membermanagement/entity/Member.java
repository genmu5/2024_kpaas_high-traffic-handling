package com.example.kpass.entity;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public class Member {
    private final Long memberId;
    private final UUID memberUUID;
    private String memberName;
    private final String memberEmail;
    private List<Post> posts;
    private final LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public Member(long memberId, UUID memberUUID, String memberName, String memberEmail, List<Post> posts, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.memberId = memberId;
        this.memberUUID = memberUUID;
        this.memberName = memberName;
        this.memberEmail = memberEmail;
        this.posts = posts;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public long getMemberId() {
        return memberId;
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

    public List<Post> getPosts() {
        return posts;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setMemberName(String memberName) {
        this.memberName = memberName;
        this.updatedAt = LocalDateTime.now();
    }

    public void setPosts(List<Post> posts) {
        this.posts = posts;
        this.updatedAt = LocalDateTime.now();
    }

}

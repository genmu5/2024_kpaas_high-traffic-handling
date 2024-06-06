package com.example.kpass.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public class Post {
    private final Long postId;
    private final UUID postUUID;
    private final Long memberId;
    private String title;
    private String content;
    private List<Comment> comments;
    private int likeCount;
    private Region region;
    private final LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public Post(Long postId, UUID postUUID, Long memberId, String title, String content, List<Comment> comments, int likeCount, Region region, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.postId = postId;
        this.postUUID = postUUID;
        this.memberId = memberId;
        this.title = title;
        this.content = content;
        this.comments = comments;
        this.likeCount = likeCount;
        this.region = region;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public Long getPostId() {
        return postId;
    }

    public UUID getPostUUID() { return postUUID; }

    public Long getMemberId() {
        return memberId;
    }

    public String getTitle() { return title; }

    public String getContent() {
        return content;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public int getLikeCount() {
        return likeCount;
    }

    public Region getRegion() {
        return region;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setContent(String content) {
        this.content = content;
        this.updatedAt = LocalDateTime.now();
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
        this.updatedAt = LocalDateTime.now();
    }

    public void setLikeCount(int likeCount) {
        this.likeCount = likeCount;
        this.updatedAt = LocalDateTime.now();
    }

    public void setRegion(Region region) {
        this.region = region;
        this.updatedAt = LocalDateTime.now();
    }

}

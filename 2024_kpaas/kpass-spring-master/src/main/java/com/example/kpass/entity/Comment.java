package com.example.kpass.entity;

import java.time.LocalDateTime;
import java.util.UUID;

public class Comment {
    private final Long commentId;
    private final UUID commentUUID;
    private final Long postId;
    private String content;
    private int likeCount;
    private final LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public Comment(Long commentId, UUID commentUUID, Long postId, String content, int likeCount, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.commentId = commentId;
        this.commentUUID = commentUUID;
        this.postId = postId;
        this.content = content;
        this.likeCount = likeCount;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public Long getCommentId() {
        return commentId;
    }

    public UUID getCommentUUID() {
        return commentUUID;
    }

    public Long getPostId() {
        return postId;
    }

    public String getContent() {
        return content;
    }

    public int getLikeCount() {
        return likeCount;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setContent(String content) {
        this.content = content;
        this.updatedAt = LocalDateTime.now();
    }

    public void setLikeCount(int likeCount) {
        this.likeCount = likeCount;
        this.updatedAt = LocalDateTime.now();
    }
}

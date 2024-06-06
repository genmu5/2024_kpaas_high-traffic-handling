package com.example.kpass.entity.dto;

import com.example.kpass.entity.Comment;

import java.time.LocalDateTime;
import java.util.UUID;

public class CommentResponseDto {
    private UUID commentUUID;
    private String content;
    private int likeCount;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public CommentResponseDto(UUID commentUUID, String content, int likeCount, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.commentUUID = commentUUID;
        this.content = content;
        this.likeCount = likeCount;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public static CommentResponseDto fromEntity(Comment comment) {
        return new CommentResponseDto(
                comment.getCommentUUID(),
                comment.getContent(),
                comment.getLikeCount(),
                comment.getCreatedAt(),
                comment.getUpdatedAt()
        );
    }

    public UUID getCommentUUID() {
        return commentUUID;
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
}

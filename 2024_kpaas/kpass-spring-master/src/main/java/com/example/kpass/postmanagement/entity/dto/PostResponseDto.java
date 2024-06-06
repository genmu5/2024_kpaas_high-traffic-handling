package com.example.kpass.entity.dto;

import com.example.kpass.entity.Member;
import com.example.kpass.entity.Post;
import com.example.kpass.entity.Region;

import java.time.LocalDateTime;
import java.util.UUID;

public class PostResponseDto {
    private UUID postUUID;
    private String title;
    private String content;
    private int likeCount;
    private Region region;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public PostResponseDto(UUID postUUID, String title, String content, int likeCount, Region region, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.postUUID = postUUID;
        this.title = title;
        this.content = content;
        this.likeCount = likeCount;
        this.region = region;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public static PostResponseDto fromEntity(Post post) {
        return new PostResponseDto(
                post.getPostUUID(),
                post.getTitle(),
                post.getContent(),
                post.getLikeCount(),
                post.getRegion(),
                post.getCreatedAt(),
                post.getUpdatedAt()
        );
    }

    public UUID getPostUUID() {
        return postUUID;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
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
}

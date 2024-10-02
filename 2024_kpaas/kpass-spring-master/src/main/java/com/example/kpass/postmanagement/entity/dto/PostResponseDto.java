package com.example.kpass.postmanagement.entity.dto;

import com.example.kpass.postmanagement.entity.Post;
import com.example.kpass.postmanagement.entity.Region;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class PostResponseDto {
    private long postId;
    private String title;
    private String content;
    private long likeCount;
    private Region region;
    private LocalDateTime createdAt;

    public static PostResponseDto fromEntity(Post post) {
        return new PostResponseDto(
                post.getPostId(),
                post.getTitle(),
                post.getContent(),
                post.getLikeCount(),
                post.getRegion(),
                post.getCreatedAt()
        );
    }
}

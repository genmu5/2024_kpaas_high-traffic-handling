package com.example.kpass.postmanagement.entity.dto;

import com.example.kpass.postmanagement.entity.Comment;
import com.example.kpass.postmanagement.entity.Post;
import com.example.kpass.postmanagement.entity.Region;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@AllArgsConstructor
public class PostResponseDto {
    private long postId;
    private String title;
    private String content;
    private long likeCount;
    private Region region;
    private List<CommentResponseDto> commentList;
    private LocalDateTime createdAt;

    public static PostResponseDto fromEntity(Post post) {
        List<CommentResponseDto> commentResponseDtos = post.getComments().stream()
                .map(CommentResponseDto::fromEntity)
                .collect(Collectors.toList());

        return new PostResponseDto(
                post.getPostId(),
                post.getTitle(),
                post.getContent(),
                post.getLikeCount(),
                post.getRegion(),
                commentResponseDtos,
                post.getCreatedAt()
        );
    }
}

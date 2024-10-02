package com.example.kpass.postmanagement.entity.dto;

import com.example.kpass.postmanagement.entity.Comment;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class CommentResponseDto {
    private Long commentId;
    private String content;
    private LocalDateTime createdAt;


    public static CommentResponseDto fromEntity(Comment comment) {
        return new CommentResponseDto(
                comment.getCommentId(),
                comment.getContent(),
                comment.getCreatedAt()
        );
    }

}

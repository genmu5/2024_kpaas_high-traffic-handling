package com.example.kpass.entity.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.UUID;

public class CommentRequestDto {
    private String content;
    private int likeCount;

    public CommentRequestDto(String content, int likeCount) {
        this.content = content;
        this.likeCount = likeCount;
    }

    public String getContent() {
        return content;
    }

    public int getLikeCount() {
        return likeCount;
    }
}

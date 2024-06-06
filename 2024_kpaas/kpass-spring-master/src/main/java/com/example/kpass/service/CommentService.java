package com.example.kpass.service;

import com.example.kpass.entity.dto.CommentRequestDto;
import com.example.kpass.entity.dto.CommentResponseDto;

import java.util.List;
import java.util.UUID;

public interface CommentService {
    CommentResponseDto insertComment(UUID postUUID, CommentRequestDto commentRequestDto);
    CommentResponseDto updateComment(UUID commentUUID, CommentRequestDto commentRequestDto);
    void deleteComment(UUID commentUUID);
    List<CommentResponseDto> findAllComments();
    List<CommentResponseDto> findCommentsByPostUUID(UUID postUUID);
}

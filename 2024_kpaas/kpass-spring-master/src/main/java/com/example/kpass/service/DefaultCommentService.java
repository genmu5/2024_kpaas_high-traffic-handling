package com.example.kpass.service;

import com.example.kpass.entity.Comment;
import com.example.kpass.entity.Member;
import com.example.kpass.entity.Post;
import com.example.kpass.entity.dto.CommentRequestDto;
import com.example.kpass.entity.dto.CommentResponseDto;
import com.example.kpass.repository.CommentRepository;
import com.example.kpass.repository.PostRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class DefaultCommentService implements CommentService {
    private final CommentRepository commentRepository;
    private final PostRepository postRepository;

    public DefaultCommentService(CommentRepository commentRepository, PostRepository postRepository) {
        this.commentRepository = commentRepository;
        this.postRepository = postRepository;
    }

    @Override
    public CommentResponseDto insertComment(UUID postUUID, CommentRequestDto commentRequestDto) {
        Post post = postRepository.findPostByUUID(postUUID).orElseThrow(() -> new RuntimeException("Post not found"));
        Comment comment = new Comment(
                0L, // 임시 ID 값 설정
                UUID.randomUUID(),
                post.getPostId(),
                commentRequestDto.getContent(),
                0,
                LocalDateTime.now(),
                LocalDateTime.now()
        );
        commentRepository.insertComment(comment);
        return CommentResponseDto.fromEntity(comment);
    }

    @Override
    public CommentResponseDto updateComment(UUID commentUUID, CommentRequestDto commentRequestDto) {
        Comment comment = commentRepository.findByCommentUUID(commentUUID).orElseThrow(() -> new RuntimeException("Comment not found"));
        Comment updatedComment = new Comment(
                comment.getCommentId(),
                comment.getCommentUUID(),
                comment.getPostId(),
                commentRequestDto.getContent(),
                commentRequestDto.getLikeCount(),
                comment.getCreatedAt(),
                LocalDateTime.now()
        );
        commentRepository.updateComment(updatedComment);
        return CommentResponseDto.fromEntity(updatedComment);
    }

    @Override
    public void deleteComment(UUID commentUUID) {
        Comment comment = commentRepository.findByCommentUUID(commentUUID).orElseThrow(() -> new RuntimeException("Comment not found"));
        commentRepository.deleteComment(comment);
    }

    @Override
    public List<CommentResponseDto> findAllComments() {
        return commentRepository.findAllComments().stream()
                .map(CommentResponseDto::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    public List<CommentResponseDto> findCommentsByPostUUID(UUID postUUID) {
        Post post = postRepository.findPostByUUID(postUUID).orElseThrow(() -> new RuntimeException("Member not found"));
        return commentRepository.findByPostId(post.getPostId()).stream()
                .map(CommentResponseDto::fromEntity)
                .collect(Collectors.toList());
    }
}

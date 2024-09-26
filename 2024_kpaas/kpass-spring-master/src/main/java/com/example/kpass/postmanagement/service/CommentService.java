package com.example.kpass.postmanagement.service;

import org.springframework.stereotype.Service;

@Service
public class CommentService {
    private final CommentRepository commentRepository;
    private final PostRepository postRepository;

    public CommentService(CommentRepository commentRepository, PostRepository postRepository) {
        this.commentRepository = commentRepository;
        this.postRepository = postRepository;
    }

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

    public void deleteComment(UUID commentUUID) {
        Comment comment = commentRepository.findByCommentUUID(commentUUID).orElseThrow(() -> new RuntimeException("Comment not found"));
        commentRepository.deleteComment(comment);
    }

    public List<CommentResponseDto> findAllComments() {
        return commentRepository.findAllComments().stream()
                .map(CommentResponseDto::fromEntity)
                .collect(Collectors.toList());
    }

    public List<CommentResponseDto> findCommentsByPostUUID(UUID postUUID) {
        Post post = postRepository.findPostByUUID(postUUID).orElseThrow(() -> new RuntimeException("Member not found"));
        return commentRepository.findByPostId(post.getPostId()).stream()
                .map(CommentResponseDto::fromEntity)
                .collect(Collectors.toList());
    }
}

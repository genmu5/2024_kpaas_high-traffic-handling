package com.example.kpass.postmanagement.service;

import com.example.kpass.postmanagement.entity.Comment;
import com.example.kpass.postmanagement.entity.Post;
import com.example.kpass.postmanagement.entity.dto.CommentRequestDto;
import com.example.kpass.postmanagement.entity.dto.CommentResponseDto;
import com.example.kpass.postmanagement.repository.CommentRepository;
import com.example.kpass.postmanagement.repository.PostRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.time.LocalDateTime;
import java.util.stream.Collectors;

@Service
public class CommentService {
    private final CommentRepository commentRepository;
    private final PostRepository postRepository;

    public CommentService(CommentRepository commentRepository, PostRepository postRepository) {
        this.commentRepository = commentRepository;
        this.postRepository = postRepository;
    }

    public CommentResponseDto insertComment(CommentRequestDto commentRequestDto) {
        Post post = postRepository.findPostByPostId(commentRequestDto.getPostId()).orElseThrow(() -> new RuntimeException("Post not found"));
        Comment comment = new Comment(
                0L, // 임시 ID 값 설정
                post,
                commentRequestDto.getContent(),
                LocalDateTime.now()
        );
        commentRepository.save(comment);
        System.out.println(comment);
        return CommentResponseDto.fromEntity(comment);
    }

    public void deleteComment(Long commentId) {
        Comment comment = commentRepository.findCommentByCommentId(commentId).orElseThrow(() -> new RuntimeException("Comment not found"));
        commentRepository.delete(comment);
    }

    public List<CommentResponseDto> findCommentsByPostId(Long postId) {
        Post post = postRepository.findPostByPostId(postId).orElseThrow(() -> new RuntimeException("Member not found"));
        return commentRepository.findByPost(post).stream()
                .map(CommentResponseDto::fromEntity)
                .collect(Collectors.toList());
    }
}

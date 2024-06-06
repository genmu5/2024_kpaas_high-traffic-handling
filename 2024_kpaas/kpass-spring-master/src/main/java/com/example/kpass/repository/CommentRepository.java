package com.example.kpass.repository;

import com.example.kpass.entity.Comment;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CommentRepository {
    Comment insertComment(Comment comment);
    Comment updateComment(Comment comment);
    void deleteComment(Comment comment);
    List<Comment> findAllComments();
    void deleteAllComments();
    List<Comment> findByPostId(Long postId); // UUID -> Long으로 변경
    Optional<Comment> findByCommentUUID(UUID commentUUID); // UUID -> Long으로 변경
}

package com.example.kpass.postmanagement.repository;

import com.example.kpass.postmanagement.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {
//    Comment insertComment(Comment comment);
//    Comment updateComment(Comment comment);
//    void deleteComment(Comment comment);
//    List<Comment> findAllComments();
//    void deleteAllComments();
//    List<Comment> findByPostId(Long postId); // UUID -> Long으로 변경
//    Optional<Comment> findByCommentUUID(UUID commentUUID); // UUID -> Long으로 변경
}

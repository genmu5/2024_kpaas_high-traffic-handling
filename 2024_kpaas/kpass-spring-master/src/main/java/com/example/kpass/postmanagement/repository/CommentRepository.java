package com.example.kpass.postmanagement.repository;

import com.example.kpass.postmanagement.entity.Comment;
import com.example.kpass.postmanagement.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
    Optional<Comment> findCommentByCommentId(Long commentId);
    List<Comment> findByPost(Post post);
}

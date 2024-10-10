package com.example.kpass.postmanagement.repository;

import com.example.kpass.postmanagement.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
    Optional<Post> findPostByPostId(Long postId);
}

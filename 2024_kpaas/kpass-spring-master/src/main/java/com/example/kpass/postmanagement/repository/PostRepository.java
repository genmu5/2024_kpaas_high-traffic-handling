package com.example.kpass.repository;

import com.example.kpass.entity.Post;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface PostRepository {
    Post insertPost(Post post);
    Post updatePost(Post post);
    void deletePost(Post post);
    List<Post> findAllPosts();
    void deleteAllPosts();
    List<Post> findByMemberId(Long memberId); // UUID -> Long으로 변경
    Optional<Post> findPostByUUID(UUID postUUID); // UUID -> Long으로 변경
}

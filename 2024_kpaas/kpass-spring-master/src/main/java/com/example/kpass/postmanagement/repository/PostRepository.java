package com.example.kpass.postmanagement.repository;

import com.example.kpass.postmanagement.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
//    Post insertPost(Post post);
//    Post updatePost(Post post);
//    void deletePost(Post post);
//    List<Post> findAllPosts();
//    void deleteAllPosts();
//    List<Post> findByMemberId(Long memberId); // UUID -> Long으로 변경
//    Optional<Post> findPostByUUID(UUID postUUID); // UUID -> Long으로 변경
}

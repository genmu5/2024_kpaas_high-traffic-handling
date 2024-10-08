package com.example.kpass.postmanagement.controller;

import com.example.kpass.postmanagement.entity.dto.PostRequestDto;
import com.example.kpass.postmanagement.entity.dto.PostResponseDto;
import com.example.kpass.postmanagement.service.PostService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/posts")
public class PostRestController {
    private PostService postService;
    public PostRestController(PostService postService) {
        this.postService = postService;
    }

    @PostMapping("/create")
    public ResponseEntity<PostResponseDto> createPost(@RequestBody PostRequestDto postRequestDto) {
        return ResponseEntity.ok(postService.createPost(postRequestDto));
    }

    @DeleteMapping("/delete/{postId}")
    public ResponseEntity<Void> deletePost(@PathVariable Long postId) {
        postService.deletePost(postId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{postId}")
    public ResponseEntity<PostResponseDto> getPostByUUID(@PathVariable Long postId) {
        return ResponseEntity.ok(postService.getPostByPostId(postId));
    }

    @GetMapping
    public ResponseEntity<Page<PostResponseDto>> getAllPosts(
            @RequestParam int page,
            @RequestParam int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("createdAt").descending());
        Page<PostResponseDto> posts = postService.getAllPosts(pageable);
        return ResponseEntity.ok(posts);
    }

}

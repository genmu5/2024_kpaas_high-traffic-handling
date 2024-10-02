package com.example.kpass.postmanagement.service;

import com.example.kpass.postmanagement.entity.Post;
import com.example.kpass.postmanagement.entity.dto.PostRequestDto;
import com.example.kpass.postmanagement.entity.dto.PostResponseDto;
import com.example.kpass.postmanagement.repository.PostRepository;
import com.example.kpass.sociallogin.entity.UserEntity;
import com.example.kpass.sociallogin.repository.UserRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;

@Service
public class PostService {
    private final PostRepository postRepository;
    private final UserRepository userRepository;

    public PostService(PostRepository postRepository, UserRepository userRepository) {
        this.postRepository = postRepository;
        this.userRepository = userRepository;
    }

    public PostResponseDto createPost(PostRequestDto postRequestDto) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userId = (String) authentication.getPrincipal();
        UserEntity userEntity = userRepository.findByUserId(userId);
        Post post = new Post(
                0L,
                userId,
                postRequestDto.getTitle(),
                postRequestDto.getContent(),
                0L,
                new ArrayList<>(),
                postRequestDto.getRegion(),
                LocalDateTime.now()
        );
        postRepository.save(post);
        return PostResponseDto.fromEntity(post);
    }

    public void deletePost(Long postId) {
        Post post = postRepository.findPostByPostId(postId).orElseThrow(() -> new RuntimeException("Post not found"));
        postRepository.delete(post);
    }

    public PostResponseDto getPostByPostId(Long postId) {
        Post post = postRepository.findPostByPostId(postId).orElseThrow(() -> new RuntimeException("Post not found"));
        return PostResponseDto.fromEntity(post);
    }

}

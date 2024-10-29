package com.example.kpass.postmanagement.service;

import com.example.kpass.postmanagement.entity.Post;
import com.example.kpass.postmanagement.entity.dto.PostRequestDto;
import com.example.kpass.postmanagement.entity.dto.PostResponseDto;
import com.example.kpass.postmanagement.repository.PostRepository;
import com.example.kpass.selflogin.entity.UserEntity;
import com.example.kpass.selflogin.repository.UserRepository;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;

@Service
public class PostService {
    private final PostRepository postRepository;
    private final UserRepository userRepository;

    public PostService(PostRepository postRepository, @Qualifier("selfloginUserRepository") UserRepository userRepository) {
        this.postRepository = postRepository;
        this.userRepository = userRepository;
    }

    public PostResponseDto createPost(PostRequestDto postRequestDto) {
        // 현재 인증 정보를 가져옴
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        // 인증되지 않은 사용자 처리
        if (authentication == null || !authentication.isAuthenticated() || "anonymousUser".equals(authentication.getPrincipal())) {
            throw new RuntimeException("User is not authenticated");
        }

        // 인증된 사용자의 이메일을 가져옴
        String userEmail = (String) authentication.getPrincipal();

        // 이메일을 통해 사용자 정보 조회
        UserEntity userEntity = userRepository.findByUserEmail(userEmail)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // Post 객체 생성 시 userEntity의 id를 사용
        Post post = new Post(
                0L,
                userEntity,
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
        Post post = postRepository.findPostByPostId(postId)
                .orElseThrow(() -> new RuntimeException("Post not found"));
        postRepository.delete(post);
    }

    public PostResponseDto getPostByPostId(Long postId) {
        Post post = postRepository.findPostByPostId(postId)
                .orElseThrow(() -> new RuntimeException("Post not found"));
        return PostResponseDto.fromEntity(post);
    }

    public Page<PostResponseDto> getAllPosts(Pageable pageable) {
        return postRepository.findAll(pageable).map(PostResponseDto::fromEntity);
    }
}

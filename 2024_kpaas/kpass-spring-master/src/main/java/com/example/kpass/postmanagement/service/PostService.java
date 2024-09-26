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
                new ArrayList<>(),
                0,
                postRequestDto.getRegion(),
                LocalDateTime.now()
        );
        postRepository.insertPost(post);
        return PostResponseDto.fromEntity(post);
    }

    public PostResponseDto updatePost(UUID postUUID, PostRequestDto postRequestDto) {
        Post post = postRepository.findPostByUUID(postUUID).orElseThrow(() -> new RuntimeException("Post not found"));
        Post updatedPost = new Post(
                post.getPostId(),
                post.getPostUUID(),
                post.getMemberId(),
                postRequestDto.getTitle(),
                postRequestDto.getContent(),
                post.getComments(),
                postRequestDto.getLikeCount(),
                postRequestDto.getRegion(),
                post.getCreatedAt(),
                LocalDateTime.now()
        );
        postRepository.updatePost(updatedPost);
        return PostResponseDto.fromEntity(updatedPost);
    }

    public void deletePost(UUID postUUID) {
        Post post = postRepository.findPostByUUID(postUUID).orElseThrow(() -> new RuntimeException("Post not found"));
        postRepository.deletePost(post);
    }

    public List<PostResponseDto> getAllPosts() {
        return postRepository.findAllPosts().stream()
                .map(post -> {
                    String memberEmail = memberRepository.findMemberById(post.getMemberId()).get().getMemberEmail();
                    return PostResponseDto.fromEntity(post);
                })
                .collect(Collectors.toList());
    }

    public PostResponseDto getPostByUUID(UUID postUUID) {
        Post post = postRepository.findPostByUUID(postUUID).orElseThrow(() -> new RuntimeException("Post not found"));
        String memberEmail = memberRepository.findMemberById(post.getMemberId()).get().getMemberEmail();
        return PostResponseDto.fromEntity(post);
    }

    public List<PostResponseDto> getPostsByMemberUUID(UUID memberUUID) {
        Member member = memberRepository.findMemberByUUID(memberUUID).orElseThrow(() -> new RuntimeException("Member not found"));
        return postRepository.findByMemberId(member.getMemberId()).stream()
                .map(post -> PostResponseDto.fromEntity(post))
                .collect(Collectors.toList());
    }
}

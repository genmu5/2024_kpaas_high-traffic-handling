package com.example.kpass.service;

import com.example.kpass.entity.Member;
import com.example.kpass.entity.Post;
import com.example.kpass.entity.dto.PostRequestDto;
import com.example.kpass.entity.dto.PostResponseDto;
import com.example.kpass.repository.MemberRepository;
import com.example.kpass.repository.PostRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class DefaultPostService implements PostService {
    private final PostRepository postRepository;
    private final MemberRepository memberRepository;

    public DefaultPostService(PostRepository postRepository, MemberRepository memberRepository) {
        this.postRepository = postRepository;
        this.memberRepository = memberRepository;
    }

    @Override
    public PostResponseDto createPost(UUID memberUUID, PostRequestDto postRequestDto) {
        Member member = memberRepository.findMemberByUUID(memberUUID).orElseThrow(() -> new RuntimeException("Member not found"));
        Post post = new Post(
                0L, // 임시 ID 값 설정
                UUID.randomUUID(),
                member.getMemberId(),
                postRequestDto.getTitle(),
                postRequestDto.getContent(),
                new ArrayList<>(),
                0,
                postRequestDto.getRegion(),
                LocalDateTime.now(),
                LocalDateTime.now()
        );
        postRepository.insertPost(post);
        return PostResponseDto.fromEntity(post);
    }

    @Override
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

    @Override
    public void deletePost(UUID postUUID) {
        Post post = postRepository.findPostByUUID(postUUID).orElseThrow(() -> new RuntimeException("Post not found"));
        postRepository.deletePost(post);
    }

    @Override
    public List<PostResponseDto> getAllPosts() {
        return postRepository.findAllPosts().stream()
                .map(post -> {
                    String memberEmail = memberRepository.findMemberById(post.getMemberId()).get().getMemberEmail();
                    return PostResponseDto.fromEntity(post);
                })
                .collect(Collectors.toList());
    }

    @Override
    public PostResponseDto getPostByUUID(UUID postUUID) {
        Post post = postRepository.findPostByUUID(postUUID).orElseThrow(() -> new RuntimeException("Post not found"));
        String memberEmail = memberRepository.findMemberById(post.getMemberId()).get().getMemberEmail();
        return PostResponseDto.fromEntity(post);
    }

    @Override
    public List<PostResponseDto> getPostsByMemberUUID(UUID memberUUID) {
        Member member = memberRepository.findMemberByUUID(memberUUID).orElseThrow(() -> new RuntimeException("Member not found"));
        return postRepository.findByMemberId(member.getMemberId()).stream()
                .map(post -> PostResponseDto.fromEntity(post))
                .collect(Collectors.toList());
    }
}

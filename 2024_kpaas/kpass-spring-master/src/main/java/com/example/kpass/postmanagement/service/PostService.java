package com.example.kpass.service;

import com.example.kpass.entity.dto.PostRequestDto;
import com.example.kpass.entity.dto.PostResponseDto;

import java.util.List;
import java.util.UUID;

public interface PostService {
    PostResponseDto createPost(UUID memberUUID, PostRequestDto postRequestDto);
    PostResponseDto updatePost(UUID postUUID, PostRequestDto postRequestDto);
    void deletePost(UUID postUUID);
    List<PostResponseDto> getAllPosts();
    PostResponseDto getPostByUUID(UUID postUUID);
    List<PostResponseDto> getPostsByMemberUUID(UUID memberUUID);
}

//package com.example.kpass.controller;
//
//import com.example.kpass.entity.dto.PostRequestDto;
//import com.example.kpass.entity.dto.PostResponseDto;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//import java.util.UUID;
//
//@RestController
//@RequestMapping("/api/v1/posts")
//public class PostRestController {
//    private PostService postService;
//    public PostRestController(PostService postService) {
//        this.postService = postService;
//    }
//
//    @PostMapping("/create/{memberUUID}")
//    public ResponseEntity<PostResponseDto> createPost(@PathVariable String memberUUID, @RequestBody PostRequestDto postRequestDto) {
//        UUID uuid = UUID.fromString(memberUUID);
//        return ResponseEntity.ok(postService.createPost(uuid, postRequestDto));
//    }
//
//    @PutMapping("/update/{postUUID}")
//    public ResponseEntity<PostResponseDto> updatePost(@PathVariable String postUUID, @RequestBody PostRequestDto postRequestDto) {
//        UUID uuid = UUID.fromString(postUUID);
//        return ResponseEntity.ok(postService.updatePost(uuid, postRequestDto));
//    }
//
//    @DeleteMapping("/delete/{postUUID}")
//    public ResponseEntity<Void> deletePost(@PathVariable String postUUID) {
//        UUID uuid = UUID.fromString(postUUID);
//        postService.deletePost(uuid);
//        return ResponseEntity.noContent().build();
//    }
//
//    @GetMapping
//    public ResponseEntity<List<PostResponseDto>> getAllPosts() {
//        return ResponseEntity.ok(postService.getAllPosts());
//    }
//
//    @GetMapping("/{postUUID}")
//    public ResponseEntity<PostResponseDto> getPostByUUID(@PathVariable String postUUID) {
//        UUID uuid = UUID.fromString(postUUID);
//        return ResponseEntity.ok(postService.getPostByUUID(uuid));
//    }
//
//    @GetMapping("/member/{memberUUID}")
//    public ResponseEntity<List<PostResponseDto>> getPostsByMemberUUID(@PathVariable String memberUUID) {
//        UUID uuid = UUID.fromString(memberUUID);
//        return ResponseEntity.ok(postService.getPostsByMemberUUID(uuid));
//    }
//}

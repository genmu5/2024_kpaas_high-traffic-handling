package com.example.kpass.controller;

import com.example.kpass.entity.dto.CommentRequestDto;
import com.example.kpass.entity.dto.CommentResponseDto;
import com.example.kpass.service.CommentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/comments")
public class CommentRestController {

    private final CommentService commentService;

    public CommentRestController(CommentService commentService) {
        this.commentService = commentService;
    }

    @PostMapping("/create/{postUUID}")
    public ResponseEntity<CommentResponseDto> createComment(@PathVariable String postUUID, @RequestBody CommentRequestDto commentRequestDto) {
        UUID uuid = UUID.fromString(postUUID);
        return ResponseEntity.ok(commentService.insertComment(uuid, commentRequestDto));
    }

    @PutMapping("/update/{commentUUID}")
    public ResponseEntity<CommentResponseDto> updateComment(@PathVariable String commentUUID, @RequestBody CommentRequestDto commentRequestDto) {
        UUID uuid = UUID.fromString(commentUUID);
        return ResponseEntity.ok(commentService.updateComment(uuid, commentRequestDto));
    }

    @DeleteMapping("/delete/{commentUUID}")
    public ResponseEntity<Void> deleteComment(@PathVariable String commentUUID) {
        UUID uuid = UUID.fromString(commentUUID);
        commentService.deleteComment(uuid);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<List<CommentResponseDto>> getAllComments() {
        return ResponseEntity.ok(commentService.findAllComments());
    }

    @GetMapping("/{postUUID}")
    public ResponseEntity<List<CommentResponseDto>> getCommentsByPostId(@PathVariable String postUUID) {
        UUID uuid = UUID.fromString(postUUID);
        return ResponseEntity.ok(commentService.findCommentsByPostUUID(uuid));
    }
}

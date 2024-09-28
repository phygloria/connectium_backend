package com.ohgiraffers.crud_back.comment.controller;

import com.ohgiraffers.crud_back.comment.model.dto.CommentDTO;
import com.ohgiraffers.crud_back.comment.model.dto.CommentRequest;
import com.ohgiraffers.crud_back.comment.model.entity.Comment;
import com.ohgiraffers.crud_back.comment.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/comments")
public class CommentController {

    @Autowired
    private CommentService commentService;

    @PostMapping
    public ResponseEntity<CommentDTO> addComment(@RequestBody CommentRequest request,
                                                 @AuthenticationPrincipal UserDetails userDetails) {
        try {
            CommentDTO comment = commentService.addComment(request.getPostId(), request.getCommunityId(),
                    request.getContent(), userDetails.getUsername(), request.getParentId());
            CommentDTO savedComment = commentService.getCommentById(comment.getId());
            return ResponseEntity.status(HttpStatus.CREATED).body(savedComment);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/post/{postId}")
    public ResponseEntity<List<CommentDTO>> getCommentsByPostId(@PathVariable Long postId) {
        List<CommentDTO> comments = commentService.getCommentsByPostId(postId);
        return ResponseEntity.ok(comments);
    }

    @GetMapping("/community/{communityId}")
    public ResponseEntity<List<CommentDTO>> getCommentsByCommunityId(@PathVariable Long communityId) {
        List<CommentDTO> comments = commentService.getCommentsByCommunityId(communityId);
        return ResponseEntity.ok(comments);
    }
}


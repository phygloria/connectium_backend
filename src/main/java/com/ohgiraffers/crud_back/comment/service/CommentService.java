package com.ohgiraffers.crud_back.comment.service;

import com.ohgiraffers.crud_back.comment.model.dto.CommentDTO;
import com.ohgiraffers.crud_back.comment.model.entity.Comment;
import com.ohgiraffers.crud_back.comment.repository.CommentRepository;
import com.ohgiraffers.crud_back.login.model.entity.User;
import com.ohgiraffers.crud_back.login.service.UserService;
import com.ohgiraffers.crud_back.post.model.dto.PostDTO;
import com.ohgiraffers.crud_back.post.model.entity.PostEntity;
import com.ohgiraffers.crud_back.post.service.PostService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class CommentService {
    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private PostService postService;

    @Autowired
    private UserService userService;

    public CommentDTO addComment(Long postId, String content, String username, Long parentId) {
        PostEntity post = postService.getPostEntityById(postId)
                .orElseThrow(() -> new IllegalArgumentException("Post not found"));
        User user = userService.getUserByUsername(username);

        Comment comment = new Comment();
        comment.setPost(post);
        comment.setContent(content);
        comment.setAuthor(user.getName());
        comment.setCreatedAt(LocalDateTime.now());

        if (parentId != null) {
            Comment parentComment = commentRepository.findById(parentId)
                    .orElseThrow(() -> new IllegalArgumentException("Parent comment not found"));
            comment.setParent(parentComment);
        }

        Comment savedComment = commentRepository.save(comment);
        post.addComment(savedComment);
        postService.savePost(post);

        return convertToDTOWithReplies(savedComment);
    }

    public CommentDTO getCommentById(Long id) {
        Comment comment = commentRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Comment not found"));
        return convertToDTOWithReplies(comment);
    }

    public List<CommentDTO> getCommentsByPostId(Long postId) {
        List<Comment> comments = commentRepository.findByPostIdAndParentIsNullOrderByCreatedAtDesc(postId);
        return comments.stream()
                .map(this::convertToDTOWithReplies)
                .collect(Collectors.toList());
    }

    private CommentDTO convertToDTOWithReplies(Comment comment) {
        return new CommentDTO.Builder()
                .id(comment.getId())
                .content(comment.getContent())
                .author(comment.getAuthor())
                .postId(comment.getPost().getId())
                .parentId(comment.getParent() != null ? comment.getParent().getId() : null)
                .createdAt(comment.getCreatedAt())
                .replies(comment.getReplies().stream()
                        .map(this::convertToDTOWithReplies)
                        .collect(Collectors.toList()))
                .build();
    }

    private PostEntity convertToPostEntity(PostDTO postDTO) {
        return new PostEntity.Builder()
                .id(postDTO.getId())
                .title(postDTO.getTitle())
                .content(postDTO.getContent())
                .author(postDTO.getAuthor())
                .imagePath(postDTO.getImagePath())
                .viewCount(postDTO.getViewCount())
                .build();
    }
}
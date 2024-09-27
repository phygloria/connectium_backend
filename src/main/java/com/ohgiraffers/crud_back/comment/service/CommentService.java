package com.ohgiraffers.crud_back.comment.service;

import com.ohgiraffers.crud_back.comment.model.dto.CommentDTO;
import com.ohgiraffers.crud_back.comment.model.entity.Comment;
import com.ohgiraffers.crud_back.comment.repository.CommentRepository;
import com.ohgiraffers.crud_back.community.model.entity.CommunityEntity;
import com.ohgiraffers.crud_back.community.service.CommunityService;
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
    private CommunityService communityService;  // CommunityEntity 처리를 위한 서비스

    @Autowired
    private UserService userService;

    public CommentDTO addComment(Long postId, Long communityId, String content, String username, Long parentId) {
        User user = userService.getUserByUsername(username);

        Comment comment = new Comment();
        comment.setContent(content);
        comment.setAuthor(user.getName());
        comment.setCreatedAt(LocalDateTime.now());

        if (postId != null) {
            PostEntity post = postService.getPostEntityById(postId)
                    .orElseThrow(() -> new IllegalArgumentException("Post not found"));
            comment.setPost(post);
        } else if (communityId != null) {
            CommunityEntity community = communityService.getCommunityEntityById(communityId)
                    .orElseThrow(() -> new IllegalArgumentException("Community not found"));
            comment.setCommunity(community);
        } else {
            throw new IllegalArgumentException("Either postId or communityId must be provided");
        }

        if (parentId != null) {
            Comment parentComment = commentRepository.findById(parentId)
                    .orElseThrow(() -> new IllegalArgumentException("Parent comment not found"));
            comment.setParent(parentComment);
        }

        Comment savedComment = commentRepository.save(comment);

        return convertToDTOWithReplies(savedComment);
    }

    public List<CommentDTO> getCommentsByPostId(Long postId) {
        List<Comment> comments = commentRepository.findByPostIdAndParentIsNullOrderByCreatedAtDesc(postId);
        return comments.stream()
                .map(this::convertToDTOWithReplies)
                .collect(Collectors.toList());
    }

    // 이 메서드가 필요한 이유는 댓글 저장 후 그 댓글을 다시 조회해 클라이언트에게 반환하기 위해서입니다.
    public CommentDTO getCommentById(Long id) {
        Comment comment = commentRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Comment not found"));
        return convertToDTOWithReplies(comment);
    }

    public List<CommentDTO> getCommentsByCommunityId(Long communityId) {
        List<Comment> comments = commentRepository.findByCommunityIdAndParentIsNullOrderByCreatedAtDesc(communityId);
        return comments.stream()
                .map(this::convertToDTOWithReplies)
                .collect(Collectors.toList());
    }

    private CommentDTO convertToDTOWithReplies(Comment comment) {
        return new CommentDTO.Builder()
                .id(comment.getId())
                .content(comment.getContent())
                .author(comment.getAuthor())
                .postId(comment.getPost() != null ? comment.getPost().getId() : null)
                .communityId(comment.getCommunity() != null ? comment.getCommunity().getId() : null)
                .parentId(comment.getParent() != null ? comment.getParent().getId() : null)
                .createdAt(comment.getCreatedAt())
                .replies(comment.getReplies().stream()
                        .map(this::convertToDTOWithReplies)
                        .collect(Collectors.toList()))
                .build();
    }


}

package com.ohgiraffers.crud_back.comment.model.dto;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class CommentDTO {

    private Long id;
    private String content;
    private String author;
    private Long postId;
    private Long communityId; // CommunityId 추가
    private Long parentId;
    private LocalDateTime createdAt;
    private List<CommentDTO> replies;

    private CommentDTO(Builder builder) {
        this.id = builder.id;
        this.content = builder.content;
        this.author = builder.author;
        this.postId = builder.postId;
        this.communityId = builder.communityId;  // CommunityId 추가
        this.parentId = builder.parentId;
        this.createdAt = builder.createdAt;
        this.replies = builder.replies;
    }

    public static class Builder {
        private Long id;
        private String content;
        private String author;
        private Long postId;
        private Long communityId; // CommunityId 필드 추가
        private Long parentId;
        private LocalDateTime createdAt;
        private List<CommentDTO> replies = new ArrayList<>();

        public Builder id(Long id) {
            this.id = id;
            return this;
        }

        public Builder content(String content) {
            this.content = content;
            return this;
        }

        public Builder author(String author) {
            this.author = author;
            return this;
        }

        public Builder postId(Long postId) {
            this.postId = postId;
            return this;
        }

        public Builder communityId(Long communityId) {  // CommunityId Builder 추가
            this.communityId = communityId;
            return this;
        }

        public Builder parentId(Long parentId) {
            this.parentId = parentId;
            return this;
        }

        public Builder createdAt(LocalDateTime createdAt) {
            this.createdAt = createdAt;
            return this;
        }

        public Builder replies(List<CommentDTO> replies) {
            this.replies = replies;
            return this;
        }

        public CommentDTO build() {
            return new CommentDTO(this);
        }
    }

    // Getters
    public Long getId() {
        return id;
    }

    public String getContent() {
        return content;
    }

    public String getAuthor() {
        return author;
    }

    public Long getPostId() {
        return postId;
    }

    public Long getCommunityId() {  // Getter 추가
        return communityId;
    }

    public Long getParentId() {
        return parentId;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public List<CommentDTO> getReplies() {
        return replies;
    }
}


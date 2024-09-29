package com.ohgiraffers.crud_back.community.model.entity;

import com.ohgiraffers.crud_back.comment.model.entity.Comment;
import com.ohgiraffers.crud_back.utility.post.BaseEntity;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "community_tables")
public class CommunityEntity extends BaseEntity {

    @Column(nullable = false)
    private String category;

    @OneToMany(mappedBy = "community", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Comment> comments = new ArrayList<>();

    protected CommunityEntity() {
        super();
    }

    private CommunityEntity(Builder builder) {
        super(builder.id, builder.title, builder.author, builder.content, builder.viewCount);
        this.category = builder.category;
        this.comments = builder.comments != null ? builder.comments : new ArrayList<>();
    }

    public static class Builder {
        private Long id;
        private String title;
        private String content;
        private String author;
        private String category;
        private int viewCount = 0;
        private List<Comment> comments;

        public Builder id(Long id) {
            this.id = id;
            return this;
        }

        public Builder title(String title) {
            this.title = title;
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

        public Builder category(String category) {
            this.category = category;
            return this;
        }

        public Builder viewCount(int viewCount) {
            this.viewCount = viewCount;
            return this;
        }

        public Builder comments(List<Comment> comments) {
            this.comments = comments;
            return this;
        }

        public CommunityEntity build() {
            return new CommunityEntity(this);
        }
    }

    // Getters
    public String getCategory() {
        return category;
    }

    public List<Comment> getComments() {
        return comments;
    }

    // Setters
    public void setCategory(String category) {
        this.category = category;
    }

    public void incrementViewCount() {
        this.setViewCount(this.getViewCount() + 1);
    }

    // 댓글 추가 및 삭제
    public void addComment(Comment comment) {
        comments.add(comment);
        comment.setCommunity(this);
    }

    public void removeComment(Comment comment) {
        comments.remove(comment);
        comment.setCommunity(null);
    }

    // 복사 후 조회수 증가 메서드
    public CommunityEntity copyWithIncrementedViewCount() {
        return new Builder()
                .id(this.getId())
                .title(this.getTitle())
                .content(this.getContent())
                .author(this.getAuthor())
                .category(this.getCategory())
                .viewCount(this.getViewCount() + 1)
                .comments(new ArrayList<>(this.comments))  // 댓글 리스트도 복사
                .build();
    }
}

package com.ohgiraffers.crud_back.post.model.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.ohgiraffers.crud_back.comment.model.entity.Comment;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;


@Entity
@Table(name = "post_QnA")
public class PostEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(columnDefinition = "TEXT")
    private String content;

    @Column(nullable = false)
    private String author;

    @Column
    private String imagePath;

    @Column(nullable = false)
    private int viewCount = 0;

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<Comment> comments = new ArrayList<>();

    protected PostEntity() {}

    private PostEntity(Builder builder) {
        this.id = builder.id;
        this.title = builder.title;
        this.content = builder.content;
        this.author = builder.author;
        this.imagePath = builder.imagePath;
        this.viewCount = builder.viewCount;
    }

    public static class Builder {
        private Long id;
        private String title;
        private String content;
        private String author;
        private String imagePath;
        private int viewCount = 0;

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

        public Builder imagePath(String imagePath) {
            this.imagePath = imagePath;
            return this;
        }

        public Builder viewCount(int viewCount) {
            this.viewCount = viewCount;
            return this;
        }

        public PostEntity build() {
            return new PostEntity(this);
        }
    }

    // Getters
    public Long getId() { return id; }
    public String getTitle() { return title; }
    public String getContent() { return content; }
    public String getAuthor() { return author; }
    public String getImagePath() { return imagePath; }
    public int getViewCount() { return viewCount; }
    public List<Comment> getComments() { return comments; }

    // Setters
    public void setImagePath(String imageUrl) {
        this.imagePath = imageUrl;
    }

    public void incrementViewCount() {
        this.viewCount++;
    }

    // Comment related methods
    public void addComment(Comment comment) {
        comments.add(comment);
        comment.setPost(this);
    }

    public void removeComment(Comment comment) {
        comments.remove(comment);
        comment.setPost(null);
    }

    // New method for copying with incremented view count
    public PostEntity copyWithIncrementedViewCount() {
        PostEntity newPost = new PostEntity.Builder()
                .id(this.getId())
                .title(this.getTitle())
                .content(this.getContent())
                .author(this.getAuthor())
                .imagePath(this.getImagePath())
                .viewCount(this.getViewCount() + 1)
                .build();
        newPost.comments = new ArrayList<>(this.comments);
        return newPost;
    }
}
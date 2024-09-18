package com.ohgiraffers.crud_back.post.model.entity;

import jakarta.persistence.*;


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

    // Setters
    public void setImagePath(String imageUrl) {
        this.imagePath = imageUrl;
    }

    public void incrementViewCount() {
        this.viewCount++;
    }

    // No-args constructor for JPA
    protected PostEntity() {}

}
package com.ohgiraffers.crud_back.community.model.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "community_tables")
public class CommunityEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String author;

    @Column(nullable = false, length = 1000)
    private String content;

    @Column(nullable = false)
    private String category;

    @Column(nullable = false)
    private int viewCount = 0;

    protected CommunityEntity() {
    }

    private CommunityEntity(Builder builder) {
        this.id = builder.id;
        this.title = builder.title;
        this.author = builder.author;
        this.content = builder.content;
        this.category = builder.category;
        this.viewCount = builder.viewCount;
    }

    public static class Builder {
        private Long id;
        private String title;
        private String author;
        private String content;
        private String category;
        private int viewCount = 0;

        public Builder id(Long id) {
            this.id = id;
            return this;
        }

        public Builder title(String title) {
            this.title = title;
            return this;
        }

        public Builder author(String author) {
            this.author = author;
            return this;
        }

        public Builder content(String content) {
            this.content = content;
            return this;
        }

        public Builder category(String category){
            this.category = category;
            return this;
        }

        public Builder viewCount(int viewCount){
            this.viewCount = viewCount;
            return this;
        }

        public CommunityEntity build() { return new CommunityEntity(this); }

    }

    public Long getId() { return id; }
    public String getTitle() { return title; }
    public String getAuthor() { return author; }
    public String getContent() { return content; }
    public String getCategory() { return category; }
    public int getViewCount() { return viewCount; }

//     New method for copying with incremented view count
    public CommunityEntity copyWithIncrementedViewCount() {
        CommunityEntity newCommunity = new Builder()
                .id(this.getId())
                .title(this.getTitle())
                .author(this.getAuthor())
                .content(this.getContent())
                .category(this.getCategory())  // 이 줄을 추가
                .viewCount(this.getViewCount() + 1)
                .build();

        return newCommunity;
    }
}
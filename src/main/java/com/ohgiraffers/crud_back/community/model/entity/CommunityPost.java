package com.ohgiraffers.crud_back.community.model.entity;

import com.ohgiraffers.crud_back.post.model.entity.PostEntity;
import jakarta.persistence.*;

import java.util.ArrayList;

@Entity
@Table(name = "community_posts")
public class CommunityPost {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String authorName;

    @Column(nullable = false, length = 1000)
    private String content;

    @Column(nullable = false)
    private String category;

    @Column(nullable = false)
    private int viewCount = 0;

    public CommunityPost() {
    }

    public CommunityPost(Long id, String title, String authorName, String content, String category, int viewCount) {
        this.id = id;
        this.title = title;
        this.authorName = authorName;
        this.content = content;
        this.category = category;
        this.viewCount = viewCount;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public int getViewCount() {
        return viewCount;
    }

    public void setViewCount(int viewCount) {
        this.viewCount = viewCount;
    }

    public void incrementViewCount() {
        this.viewCount++;
    }

    // New method for copying with incremented view count
    public CommunityPost copyWithIncrementedViewCount() {
        return new CommunityPost(
                this.getId(),
                this.getTitle(),
                this.getAuthorName(),
                this.getContent(),
                this.getCategory(),
                this.getViewCount() + 1
        );
    }

    @Override
    public String toString() {
        return "CommunityPost{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", authorName='" + authorName + '\'' +
                ", content='" + content + '\'' +
                ", category='" + category + '\'' +
                ", viewCount=" + viewCount +
                '}';
    }
}
package com.ohgiraffers.crud_back.community.model.dto;

public class CommunityPostDTO {
    private Long id;
    private String title;
    private String authorName;
    private String content;
    private String category;

    public CommunityPostDTO() {
    }

    public CommunityPostDTO(Long id, String title, String authorName, String content, String category) {
        this.id = id;
        this.title = title;
        this.authorName = authorName;
        this.content = content;
        this.category = category;
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

    @Override
    public String toString() {
        return "CommunityPostDTO{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", authorName='" + authorName + '\'' +
                ", content='" + content + '\'' +
                ", category='" + category + '\'' +
                '}';
    }
}
package com.ohgiraffers.crud_back.post.model.dto;

public class PostDTO {

    private Long id;
    private String title;
    private String content;
    private String author;
    private String imagePath;

    private PostDTO(Builder builder) {
        this.id = builder.id;
        this.title = builder.title;
        this.content = builder.content;
        this.author = builder.author;
        this.imagePath = builder.imagePath;
    }

    public static class Builder{

        private Long id;
        private String title;
        private String content;
        private String author;
        private String imagePath;

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

        public PostDTO build() {
            return new PostDTO(this);
        }

    }

    public Long getId() { return id; }
    public String getTitle() { return title; }
    public String getContent() { return content; }
    public String getAuthor() { return author; }
    public String getImagePath() { return imagePath; }

    public void setImagePath(String imageUrl) {
        this.imagePath = imageUrl;
    }

    // toString method
    @Override
    public String toString() {
        return "PostDTO{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", author='" + author + '\'' +
                ", imagePath='" + imagePath + '\'' +
                '}';
    }
}
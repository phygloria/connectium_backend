package com.ohgiraffers.crud_back.community.model.dto;

public class CommunityDTO {
    private Long id;
    private String title;
    private String authorName;
    private String content;
    private String category;
    private int viewCount;

    public CommunityDTO() {}

    public CommunityDTO(Builder builder) {
        this.id = builder.id;
        this.title = builder.title;
        this.authorName = builder.authorName;
        this.content = builder.content;
        this.category = builder.category;
        this.viewCount = builder.viewCount;
    }

    public static class Builder {

        private Long id;
        private String title;
        private String authorName;
        private String content;
        private String category;
        private int viewCount;

        public Builder id(Long id) {
            this.id = id;
            return this;
        }

        public Builder title(String title) {
            this.title = title;
            return this;
        }

        public Builder authorName(String authorName) {
            this.authorName = authorName;
            return this;
        }

        public Builder content(String content) {
            this.content = content;
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

        public CommunityDTO build() {
            return new CommunityDTO(this);
        }
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthorName() {
        return authorName;
    }

    public String getContent() {
        return content;
    }

    public String getCategory() {
        return category;
    }

    public int getViewCount() {
        return viewCount;
    }

}

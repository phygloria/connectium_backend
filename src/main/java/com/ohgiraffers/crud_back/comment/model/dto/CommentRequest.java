package com.ohgiraffers.crud_back.comment.model.dto;

public class CommentRequest {
    private Long postId;
    private Long communityId;
    private String content;
    private Long parentId;

    // Getters and Setters
    public Long getPostId() {
        return postId;
    }

    public void setPostId(Long postId) {
        this.postId = postId;
    }

    public Long getCommunityId() {
        return communityId;
    }

    public void setCommunityId(Long communityId) {
        this.communityId = communityId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    // 추가: toString 메서드 오버라이드
    @Override
    public String toString() {
        return "CommentRequest{" +
                "postId=" + postId +
                ", communityId=" + communityId +
                ", content='" + content + '\'' +
                ", parentId=" + parentId +
                '}';
    }
}

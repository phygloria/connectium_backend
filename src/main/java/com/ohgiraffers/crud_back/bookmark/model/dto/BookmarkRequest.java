package com.ohgiraffers.crud_back.bookmark.model.dto;

public class BookmarkRequest {
    private String itemId;
    private String itemType;


    public BookmarkRequest() {
    }

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public String getItemType() {
        return itemType;
    }

    public void setItemType(String itemType) {
        this.itemType = itemType;
    }
}

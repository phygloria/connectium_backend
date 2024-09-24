package com.ohgiraffers.crud_back.bookmark.model.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "bookmarks")
public class Bookmark {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String itemId;

    @Column(nullable = false)
    private String itemType;

    @Column(nullable = false)
    private String username;

    public Bookmark() {
    }

    public Bookmark(Long id, String itemId, String itemType, String username) {
        this.id = id;
        this.itemId = itemId;
        this.itemType = itemType;
        this.username = username;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public String toString() {
        return "Bookmark{" +
                "id=" + id +
                ", itemId='" + itemId + '\'' +
                ", itemType='" + itemType + '\'' +
                ", username='" + username + '\'' +
                '}';
    }
}

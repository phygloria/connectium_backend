package com.ohgiraffers.crud_back.bookmark.repository;

import com.ohgiraffers.crud_back.bookmark.model.entity.Bookmark;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BookmarkRepository extends JpaRepository<Bookmark, Long> {
    Optional<Bookmark> findByItemIdAndItemTypeAndUsername(String itemId, String itemType, String username);
    List<Bookmark> findAllByUsername(String username);
}

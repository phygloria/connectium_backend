package com.ohgiraffers.crud_back.bookmark.controller;

import com.ohgiraffers.crud_back.bookmark.model.dto.BookmarkDTO;
import com.ohgiraffers.crud_back.bookmark.model.dto.BookmarkRequest;
import com.ohgiraffers.crud_back.bookmark.service.BookmarkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/bookmarks")
public class BookmarkController {
    private final BookmarkService bookmarkService;

    @Autowired
    public BookmarkController(BookmarkService bookmarkService) {
        this.bookmarkService = bookmarkService;
    }

    @PostMapping("/toggle")
    public ResponseEntity<?> toggleBookmark(@RequestBody BookmarkRequest request, @AuthenticationPrincipal UserDetails userDetails) {
        bookmarkService.toggleBookmark(request.getItemId(), request.getItemType(), userDetails.getUsername());
        return ResponseEntity.ok().build();
    }

    @GetMapping
    public ResponseEntity<List<BookmarkDTO>> getBookmarks(@AuthenticationPrincipal UserDetails userDetails) {
        List<BookmarkDTO> bookmarks = bookmarkService.getBookmarkedItems(userDetails.getUsername());
        return ResponseEntity.ok(bookmarks);
    }
}
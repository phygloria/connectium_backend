package com.ohgiraffers.crud_back.bookmark.service;

import com.ohgiraffers.crud_back.bookmark.model.dto.BookmarkDTO;
import com.ohgiraffers.crud_back.bookmark.model.entity.Bookmark;
import com.ohgiraffers.crud_back.bookmark.repository.BookmarkRepository;
import com.ohgiraffers.crud_back.education.repository.EducationRepository;
import com.ohgiraffers.crud_back.outdoor.repository.OutdoorRepository;
import com.ohgiraffers.crud_back.program.model.dto.Program1DTO;
import com.ohgiraffers.crud_back.program.model.dto.Program2DTO;
import com.ohgiraffers.crud_back.program.service.Program1Service;
import com.ohgiraffers.crud_back.program.service.Program2Service;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class BookmarkService {
    private final BookmarkRepository bookmarkRepository;
    private final EducationRepository educationRepository;
    private final OutdoorRepository outdoorRepository;
    private final Program1Service program1Service;
    private final Program2Service program2Service;

    @Autowired
    public BookmarkService(BookmarkRepository bookmarkRepository,
                           EducationRepository educationRepository,
                           OutdoorRepository outdoorRepository,
                           Program1Service program1Service,
                           Program2Service program2Service) {
        this.bookmarkRepository = bookmarkRepository;
        this.educationRepository = educationRepository;
        this.outdoorRepository = outdoorRepository;
        this.program1Service = program1Service;
        this.program2Service = program2Service;
    }

    @Transactional
    public void toggleBookmark(String itemId, String itemType, String username) {
        Optional<Bookmark> bookmark = bookmarkRepository.findByItemIdAndItemTypeAndUsername(itemId, itemType, username);
        if (bookmark.isPresent()) {
            bookmarkRepository.delete(bookmark.get());
        } else {
            Bookmark newBookmark = new Bookmark();
            newBookmark.setItemId(itemId);
            newBookmark.setItemType(itemType);
            newBookmark.setUsername(username);
            bookmarkRepository.save(newBookmark);
        }
    }

    public List<BookmarkDTO> getBookmarkedItems(String username) {
        List<Bookmark> bookmarks = bookmarkRepository.findAllByUsername(username);
        return bookmarks.stream()
                .map(this::convertToBookmarkDTO)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }

    private BookmarkDTO convertToBookmarkDTO(Bookmark bookmark) {
        switch (bookmark.getItemType()) {
            case "EDUCATION":
                return educationRepository.findById(Long.parseLong(bookmark.getItemId()))
                        .map(BookmarkDTO::new)
                        .orElse(null);
            case "OUTDOOR":
                return outdoorRepository.findById(Long.parseLong(bookmark.getItemId()))
                        .map(BookmarkDTO::new)
                        .orElse(null);
            case "PROGRAM1":
                Program1DTO program1 = program1Service.getProgram1Detail(bookmark.getItemId());
                return program1 != null ? new BookmarkDTO(program1) : null;
            case "PROGRAM2":
                Program2DTO program2 = program2Service.getProgram2Detail(bookmark.getItemId());
                return program2 != null ? new BookmarkDTO(program2) : null;
            default:
                return null;
        }
    }
}

package com.ohgiraffers.crud_back.review.controller;

import com.ohgiraffers.crud_back.review.model.dto.ReviewDTO;
import com.ohgiraffers.crud_back.review.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reviews")
public class ReviewController {

    @Autowired
    private ReviewService reviewService;

    @GetMapping
    public ResponseEntity<List<ReviewDTO>> getReviews(
            @RequestParam("itemType") String itemType,
            @RequestParam("itemId") String itemId) {
        List<ReviewDTO> reviews = reviewService.getReviewsByItem(itemType, itemId);
        return ResponseEntity.ok(reviews);
    }

    @PostMapping
    public ResponseEntity<ReviewDTO> addReview(
            @RequestParam("itemType") String itemType,
            @RequestParam("itemId") String itemId,
            @RequestParam("content") String content,
            @AuthenticationPrincipal UserDetails userDetails) {
        ReviewDTO newReview = reviewService.addReview(itemType, itemId, content, userDetails.getUsername());
        return ResponseEntity.ok(newReview);
    }
}

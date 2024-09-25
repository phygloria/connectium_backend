package com.ohgiraffers.crud_back.review.service;

import com.ohgiraffers.crud_back.review.model.dto.ReviewDTO;
import com.ohgiraffers.crud_back.review.model.entity.Review;
import com.ohgiraffers.crud_back.review.repository.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReviewService {

    @Autowired
    private ReviewRepository reviewRepository;

    public List<ReviewDTO> getReviewsByItem(String itemType, String itemId) {
        List<Review> reviews = reviewRepository.findByItemTypeAndItemIdOrderByCreatedAtDesc(itemType, itemId);
        return reviews.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public ReviewDTO addReview(String itemType, String itemId, String content, String userName) {
        Review review = new Review();
        review.setItemType(itemType);
        review.setItemId(itemId);
        review.setContent(content);
        review.setUserName(userName);
        review.setCreatedAt(LocalDateTime.now());

        Review savedReview = reviewRepository.save(review);
        return convertToDTO(savedReview);
    }

    private ReviewDTO convertToDTO(Review review) {
        ReviewDTO dto = new ReviewDTO();
        dto.setId(review.getId());
        dto.setItemType(review.getItemType());
        dto.setItemId(review.getItemId());
        dto.setContent(review.getContent());
        dto.setUserName(review.getUserName());
        dto.setCreatedAt(review.getCreatedAt());
        return dto;
    }
}

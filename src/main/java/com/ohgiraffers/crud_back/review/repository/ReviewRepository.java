package com.ohgiraffers.crud_back.review.repository;

import com.ohgiraffers.crud_back.review.model.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Long> {
    List<Review> findByItemTypeAndItemIdOrderByCreatedAtDesc(String itemType, String itemId);
}

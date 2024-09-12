package com.ohgiraffers.crud_back.community.repository;

import com.ohgiraffers.crud_back.community.model.entity.CommunityPost;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommunityPostRepository extends JpaRepository<CommunityPost, Long> {
    List<CommunityPost> findByCategory(String category);
}

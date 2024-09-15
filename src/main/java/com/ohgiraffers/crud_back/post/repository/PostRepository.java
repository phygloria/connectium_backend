package com.ohgiraffers.crud_back.post.repository;

import com.ohgiraffers.crud_back.post.model.entity.PostEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<PostEntity, Long> {
    List<PostEntity> findAllByOrderByIdDesc(); // Fetch posts in descending order of ID
}



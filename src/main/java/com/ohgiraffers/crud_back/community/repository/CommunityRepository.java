package com.ohgiraffers.crud_back.community.repository;

import com.ohgiraffers.crud_back.community.model.entity.CommunityEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommunityRepository extends JpaRepository<CommunityEntity, Long> {
    List<CommunityEntity> findByCategory(String category);
}

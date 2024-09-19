package com.ohgiraffers.crud_back.education.repository;

import com.ohgiraffers.crud_back.education.model.entity.EducationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EducationRepository extends JpaRepository<EducationEntity,Long> {

}

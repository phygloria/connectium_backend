package com.ohgiraffers.crud_back.care.repository;


import com.ohgiraffers.crud_back.care.model.entity.CareEntity;
import com.ohgiraffers.crud_back.education.model.entity.EducationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CareRepository extends JpaRepository<CareEntity,Long> {

}

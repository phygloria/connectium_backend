package com.ohgiraffers.crud_back.outdoor.repository;

import com.ohgiraffers.crud_back.outdoor.model.entity.OutdoorEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OutdoorRepository extends JpaRepository<OutdoorEntity,Long> {

}

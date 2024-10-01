package com.ohgiraffers.crud_back.care.service;


import com.ohgiraffers.crud_back.care.model.dto.CareDTO;
import com.ohgiraffers.crud_back.care.model.entity.CareEntity;
import com.ohgiraffers.crud_back.care.repository.CareRepository;
import com.ohgiraffers.crud_back.education.model.dto.EducationDTO;
import com.ohgiraffers.crud_back.education.model.entity.EducationEntity;
import com.ohgiraffers.crud_back.education.repository.EducationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CareService {

    @Autowired
    private CareRepository careRepository;

    // 전체목록
    public List<CareDTO> getAllCares() {
        List<CareEntity> careEntities = careRepository.findAll();
        return careEntities.stream()
                .map(this::changeToDTO)
                .collect(Collectors.toList());
    }

    // 상세조회
    public Optional<CareDTO> getCareById(Long id) {
        return careRepository.findById(id)
                .map(this::enhanceCareImageUrl);
    }

    private CareDTO enhanceCareImageUrl(CareEntity care) {
        if (care.getImagePath() != null && !care.getImagePath().isEmpty()) {
            String imageUrl = String.format("/api/images/Care service/%s", care.getImagePath());
            care.setImagePath(imageUrl);
        }
        return changeToDTO(care);
    }

    private CareDTO changeToDTO(CareEntity careEntity) {
        CareDTO dto = new CareDTO();
        dto.setId(careEntity.getId());
        dto.setName(careEntity.getName());
        dto.setAddress(careEntity.getAddress());
        dto.setPhoneNumber(careEntity.getPhoneNumber());
        dto.setIntroduction(careEntity.getIntroduction());
        dto.setOperatingHours(careEntity.getOperatingHours());
        dto.setUsageFee(careEntity.getUsageFee());
        dto.setImagePath(careEntity.getImagePath());

        return dto;
    }

}

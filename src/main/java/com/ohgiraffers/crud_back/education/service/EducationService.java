package com.ohgiraffers.crud_back.education.service;

import com.ohgiraffers.crud_back.education.model.dto.EducationDTO;
import com.ohgiraffers.crud_back.education.model.entity.EducationEntity;
import com.ohgiraffers.crud_back.education.repository.EducationRepository;
import com.ohgiraffers.crud_back.outdoor.model.dto.OutdoorDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class EducationService {

    @Autowired
    private EducationRepository educationRepository;

    // 전체목록
    public List<EducationDTO> getAllEducations() {
        List<EducationEntity> educationEntities = educationRepository.findAll();
        return educationEntities.stream()
                .map(this::changeToDTO)
                .collect(Collectors.toList());
    }

    // 상세조회
    public Optional<EducationDTO> getEducationById(Long id) {
        return educationRepository.findById(id)
                .map(this::enhanceEducationImageUrl);
    }

    private EducationDTO enhanceEducationImageUrl(EducationEntity education) {
        if (education.getImagePath() != null && !education.getImagePath().isEmpty()) {
            String imageUrl = String.format("api/images/%s", education.getImagePath());
            education.setImagePath(imageUrl);
        }
        return changeToDTO(education);
    }

    private EducationDTO changeToDTO(EducationEntity educationEntity) {
        EducationDTO dto = new EducationDTO();
        dto.setId(educationEntity.getId());
        dto.setName(educationEntity.getName());
        dto.setAddress(educationEntity.getAddress());
        dto.setPhone(educationEntity.getPhone());
        dto.setFeature(educationEntity.getFeature());
        dto.setOpr_info(educationEntity.getOpr_info());
        dto.setOpr_hours(educationEntity.getOpr_hours());
        dto.setEnt_fee(educationEntity.getEnt_fee());
        dto.setWeb_url(educationEntity.getWeb_url());
        dto.setImagePath(educationEntity.getImagePath());
        return dto;
    }
}

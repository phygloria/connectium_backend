package com.ohgiraffers.crud_back.outdoor.service;

import com.ohgiraffers.crud_back.outdoor.model.dto.OutdoorDTO;
import com.ohgiraffers.crud_back.outdoor.model.entity.OutdoorEntity;
import com.ohgiraffers.crud_back.outdoor.repository.OutdoorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class OutdoorService {

    @Autowired
    private OutdoorRepository outdoorRepository;

    // 전체목록
    public List<OutdoorDTO> getAllOutdoors() {
        List<OutdoorEntity> outdoorEntities = outdoorRepository.findAll();
        return outdoorEntities.stream()
                .map(this::changeToDTO)
                .collect(Collectors.toList());
    }

    // 상세조회
    public Optional<OutdoorDTO> getOutdoorById(Long id) {
        return outdoorRepository.findById(id)
                .map(this::enhanceOutdoorImageUrl);
    }

    private OutdoorDTO enhanceOutdoorImageUrl(OutdoorEntity outdoor) {
        if (outdoor.getImagePath() != null && !outdoor.getImagePath().isEmpty()) {
            String imageUrl = String.format("api/images/%s", outdoor.getImagePath());
            outdoor.setImagePath(imageUrl);
        }
        return changeToDTO(outdoor);
    }

    private OutdoorDTO changeToDTO(OutdoorEntity outdoorEntity) {
        OutdoorDTO dto = new OutdoorDTO();
        dto.setId(outdoorEntity.getId());
        dto.setName(outdoorEntity.getName());
        dto.setAddress(outdoorEntity.getAddress());
        dto.setPhone(outdoorEntity.getPhone());
        dto.setFeature(outdoorEntity.getFeature());
        dto.setOpr_info(outdoorEntity.getOpr_info());
        dto.setOpr_hours(outdoorEntity.getOpr_hours());
        dto.setEnt_fee(outdoorEntity.getEnt_fee());
        dto.setWeb_url(outdoorEntity.getWeb_url());
        dto.setImagePath(outdoorEntity.getImagePath());
        return dto;
    }
}

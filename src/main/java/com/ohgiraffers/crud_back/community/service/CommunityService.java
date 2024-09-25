package com.ohgiraffers.crud_back.community.service;

import com.ohgiraffers.crud_back.community.model.dto.CommunityDTO;
import com.ohgiraffers.crud_back.community.model.entity.CommunityEntity;
import com.ohgiraffers.crud_back.community.repository.CommunityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommunityService {
    private final CommunityRepository repository;

    @Autowired
    public CommunityService(CommunityRepository repository) {
        this.repository = repository;
    }

    public List<CommunityDTO> getAllPosts() {
        return repository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public List<CommunityDTO> getPostsByCategory(String category) {
        return repository.findByCategory(category).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public CommunityDTO getPost(Long id) {
        return repository.findById(id)
                .map(post -> {
                    CommunityEntity updatedPost = post.copyWithIncrementedViewCount();
                    CommunityEntity savedPost = repository.save(updatedPost);
                    return convertToDTO(savedPost);
                })
                .orElseThrow(() -> new RuntimeException("Post not found"));
    }

    public CommunityDTO createPost(CommunityDTO postDTO) {
        CommunityEntity post = convertToEntity(postDTO);
        return convertToDTO(repository.save(post));
    }

    public CommunityDTO updatePost(Long id, CommunityDTO communityDTO) {

//        CommunityEntity post = repository.findById(id)
//                .orElseThrow(() -> new RuntimeException("Post not found"));
//        post.setTitle(communityDTO.getTitle());
//        post.setAuthorName(communityDTO.getAuthorName());
//        post.setContent(communityDTO.getContent());
//        post.setCategory(communityDTO.getCategory());
//        post.setViewCount(post.getViewCount());
//
//        return convertToDTO(repository.save(post));

        CommunityEntity post = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Post not found"));

        // 빌더 패턴을 사용하여 기존 필드 복사 후 필요한 필드만 업데이트
        CommunityEntity updatedPost = new CommunityEntity.Builder()
                .id(post.getId())  // 기존 ID 유지
                .title(communityDTO.getTitle())  // 새로운 제목으로 변경
                .authorName(communityDTO.getAuthorName())  // 새로운 작성자 이름으로 변경
                .content(communityDTO.getContent())  // 새로운 내용으로 변경
                .category(communityDTO.getCategory())  // 새로운 카테고리로 변경
                .viewCount(post.getViewCount())  // 조회수는 그대로 유지
                .build();

        return convertToDTO(repository.save(updatedPost));  // 수정된 엔티티 저장 후 DTO로 반환
    }

    public void deletePost(Long id) {
        repository.deleteById(id);
    }

    private CommunityDTO convertToDTO(CommunityEntity entity) {
        return new CommunityDTO.Builder()
                .id(entity.getId())
                .title(entity.getTitle())
                .authorName(entity.getAuthorName())
                .content(entity.getContent())
                .category(entity.getCategory())
                .viewCount(entity.getViewCount())
                .build();
    }

    private CommunityEntity convertToEntity(CommunityDTO dto) {
        return new CommunityEntity.Builder()
                .id(dto.getId())
                .title(dto.getTitle())
                .authorName(dto.getAuthorName())
                .content(dto.getContent())
                .category(dto.getCategory())
                .viewCount(dto.getViewCount())
                .build();
    }
}
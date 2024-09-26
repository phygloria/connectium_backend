package com.ohgiraffers.crud_back.community.service;

import com.ohgiraffers.crud_back.community.model.dto.CommunityDTO;
import com.ohgiraffers.crud_back.community.model.entity.CommunityEntity;
import com.ohgiraffers.crud_back.community.repository.CommunityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.nio.file.AccessDeniedException;
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
                    // 권한 체크
                    if (!hasPermissionToView(post)) {
                        // 권한이 없는 경우 null을 반환하거나
                        // 사용자 정의 예외를 throw할 수 있습니다.
                        // 여기서는 null을 반환하는 예시를 보여드리겠습니다.
                        return null;
                    }

                    CommunityEntity updatedPost = post.copyWithIncrementedViewCount();
                    CommunityEntity savedPost = repository.save(updatedPost);
                    return convertToDTO(savedPost);
                })
                .orElseThrow(() -> new RuntimeException("Post not found"));
    }

    private boolean hasPermissionToView(CommunityEntity post) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null) {
            return false;
        }

        // 관리자는 모든 게시물에 접근 가능
        if (authentication.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ADMIN"))) {
            return true;
        }

        // 작성자 본인인 경우 접근 가능
        if (authentication.getName().equals(post.getAuthor())) {
            return true;
        }

        // 추가적인 권한 체크 로직을 여기에 구현할 수 있습니다.
        // 예: 특정 카테고리의 게시물에 대한 접근 권한 등

        return true; // 기본적으로 모든 인증된 사용자에게 접근 허용
    }

    public CommunityDTO createPost(CommunityDTO postDTO) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUsername = null;

        if (authentication != null && authentication.isAuthenticated()) {
            Object principal = authentication.getPrincipal();
            if (principal instanceof UserDetails) {
                currentUsername = ((UserDetails) principal).getUsername();
            } else {
                currentUsername = principal.toString();
            }
        }

        // DTO에서 authorName을 현재 사용자 이름으로 설정한 새로운 DTO를 빌더 패턴으로 생성
        CommunityDTO updatedDTO = new CommunityDTO.Builder()
                .title(postDTO.getTitle())
                .author(currentUsername) // 여기서 로그인한 사용자 이름 설정
                .content(postDTO.getContent())
                .category(postDTO.getCategory())
                .viewCount(postDTO.getViewCount())
                .build();

        // DTO를 엔티티로 변환
        CommunityEntity post = convertToEntity(updatedDTO);

        // 저장 후 DTO로 변환하여 반환
        return convertToDTO(repository.save(post));
    }

    public CommunityDTO updatePost(Long id, CommunityDTO communityDTO) {

        CommunityEntity post = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Post not found"));

        // 빌더 패턴을 사용하여 기존 필드 복사 후 필요한 필드만 업데이트
        CommunityEntity updatedPost = new CommunityEntity.Builder()
                .id(post.getId())  // 기존 ID 유지
                .title(communityDTO.getTitle())  // 새로운 제목으로 변경
                .author(communityDTO.getAuthor())  // 새로운 작성자 이름으로 변경
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
                .author(entity.getAuthor())
                .content(entity.getContent())
                .category(entity.getCategory())
                .viewCount(entity.getViewCount())
                .build();
    }

    private CommunityEntity convertToEntity(CommunityDTO dto) {
        return new CommunityEntity.Builder()
                .id(dto.getId())
                .title(dto.getTitle())
                .author(dto.getAuthor())
                .content(dto.getContent())
                .category(dto.getCategory())
                .viewCount(dto.getViewCount())
                .build();
    }
}
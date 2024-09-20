package com.ohgiraffers.crud_back.post.service;

import com.ohgiraffers.crud_back.post.model.dto.PostDTO;
import com.ohgiraffers.crud_back.post.model.entity.PostEntity;
import com.ohgiraffers.crud_back.post.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PostService {

    private final PostRepository postRepository;

    @Autowired
    public PostService(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

//    // 전체 게시글을 목록
//    public List<PostDTO> getAllPosts() {
//        return postRepository.findAllByOrderByIdDesc().stream()
//                .map(this::enhancePostWithImageUrl)
//                .collect(Collectors.toList());
//    }

    // 페이징된 게시글 전체 목록 조회
    public List<PostDTO> getAllPosts(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<PostEntity> postPage = postRepository.findAllByOrderByIdDesc(pageable);

        return postPage.getContent().stream()
                .map(this::enhancePostWithImageUrl)  // 원래의 기능을 유지하면서 페이징 적용
                .collect(Collectors.toList());
    }

    //게시글 등록
    public PostEntity createPost(PostDTO postDTO) {
        // 현재 로그인한 사용자의 정보를 가져옴
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUsername = null;

        if (authentication != null && authentication.isAuthenticated()) {
            Object principal = authentication.getPrincipal();
            if (principal instanceof UserDetails) {
                currentUsername = ((UserDetails) principal).getUsername();
            } else {
                currentUsername = principal.toString(); // UserDetails 타입이 아닌 경우 처리
            }
        }

        // author를 로그인한 사용자의 username으로 설정
        PostEntity postEntity = new PostEntity.Builder()
                .title(postDTO.getTitle())
                .content(postDTO.getContent())
                .author(currentUsername)  // 현재 로그인한 사용자의 username을 author로 설정
                .imagePath(postDTO.getImagePath())
                .viewCount(postDTO.getViewCount())
                .build();

        return postRepository.save(postEntity);
    }

    // 게시물 수정 (Builder 패턴 유지)
    public Optional<PostDTO> updatePost(Long id, PostDTO postDTO) {
        return postRepository.findById(id).map(postEntity -> {
            PostEntity updatedPostEntity = new PostEntity.Builder()
                    .id(postEntity.getId())  // 기존 ID 유지
                    .title(postDTO.getTitle() != null ? postDTO.getTitle() : postEntity.getTitle())  // 새 값 또는 기존 값 유지
                    .content(postDTO.getContent() != null ? postDTO.getContent() : postEntity.getContent())  // 새 값 또는 기존 값 유지
                    .author(postDTO.getAuthor() != null ? postDTO.getAuthor() : postEntity.getAuthor())  // 새 값 또는 기존 값 유지
                    .imagePath(postDTO.getImagePath() != null ? postDTO.getImagePath() : postEntity.getImagePath())  // 새 값 또는 기존 값 유지
                    .build();

            postRepository.save(updatedPostEntity);
            return enhancePostWithImageUrl(updatedPostEntity);
        });
    }
    // 게시물 삭제
    public boolean deletePost(Long id) {
        if (postRepository.existsById(id)) {
            postRepository.deleteById(id);
            return true;
        }
        return false;
    }
    // 게시물 상세 조회
    public Optional<PostDTO> getPostById(Long id) {
        return postRepository.findById(id).map(post -> {
            post.incrementViewCount(); // 조회수 증가
            PostEntity updatedPost = postRepository.save(post); // 변경된 조회수 저장 후 반환된 엔티티를 받음

            return enhancePostWithImageUrl(updatedPost); // 엔티티를 DTO로 변환하여 반환
        });
    }

    // 이미지 url 추가
    private PostDTO enhancePostWithImageUrl(PostEntity post) {
        if (post.getImagePath() != null && !post.getImagePath().isEmpty()) {
            String imageUrl = String.format("/api/images/%s", post.getImagePath());
            post.setImagePath(imageUrl);
        }
        return convertToDTO(post);
    }

    // entity → dto
    private PostDTO convertToDTO(PostEntity entity) {
        return new PostDTO.Builder()
                .id(entity.getId())
                .title(entity.getTitle())
                .content(entity.getContent())
                .author(entity.getAuthor())
                .imagePath(entity.getImagePath())  // 수정: imagePath 사용
                .viewCount(entity.getViewCount())
                .build();
    }
    // dto → entity
    private PostEntity toPostEntity(PostDTO postDTO) {
        PostEntity postEntity = new PostEntity.Builder()
                .title(postDTO.getTitle())
                .content(postDTO.getContent())
                .author(postDTO.getAuthor())
                .imagePath(postDTO.getImagePath())
                .viewCount(postDTO.getViewCount())
                .build();
        return postEntity;
    }

}
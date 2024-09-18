package com.ohgiraffers.crud_back.post.service;

import com.ohgiraffers.crud_back.post.model.dto.PostDTO;
import com.ohgiraffers.crud_back.post.model.entity.PostEntity;
import com.ohgiraffers.crud_back.post.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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

    // 전체 게시글을 목록
    public List<PostDTO> getAllPosts() {
        return postRepository.findAllByOrderByIdDesc().stream()
                .map(this::enhancePostWithImageUrl)
                .collect(Collectors.toList());
    }

    // PostDTO 받아서 게시글등록
    public PostEntity createPost(PostDTO postDTO){
        PostEntity postEntity = toPostEntity(postDTO);
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
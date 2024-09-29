package com.ohgiraffers.crud_back.post.service;

import com.ohgiraffers.crud_back.post.model.dto.PostDTO;
import com.ohgiraffers.crud_back.post.model.entity.PostEntity;
import com.ohgiraffers.crud_back.post.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
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

    public List<PostDTO> getAllPosts(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<PostEntity> postPage = postRepository.findAllByOrderByIdDesc(pageable);

        return postPage.getContent().stream()
                .map(this::enhancePostWithImageUrl)
                .collect(Collectors.toList());
    }

    public PostEntity createPost(PostDTO postDTO) {
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

        PostEntity postEntity = new PostEntity.Builder()
                .title(postDTO.getTitle())
                .content(postDTO.getContent())
                .author(currentUsername)
                .imagePath(postDTO.getImagePath())
                .viewCount(postDTO.getViewCount())
                .build();

        return postRepository.save(postEntity);
    }

    public Optional<PostDTO> updatePost(Long id, PostDTO postDTO) {
        return postRepository.findById(id).map(postEntity -> {
            PostEntity updatedPostEntity = new PostEntity.Builder()
                    .id(postEntity.getId())
                    .title(postDTO.getTitle() != null ? postDTO.getTitle() : postEntity.getTitle())
                    .content(postDTO.getContent() != null ? postDTO.getContent() : postEntity.getContent())
                    .author(postDTO.getAuthor() != null ? postDTO.getAuthor() : postEntity.getAuthor())
                    .imagePath(postDTO.getImagePath() != null ? postDTO.getImagePath() : postEntity.getImagePath())
                    .viewCount(postEntity.getViewCount())
                    .build();

            postRepository.save(updatedPostEntity);
            return enhancePostWithImageUrl(updatedPostEntity);
        });
    }

    public boolean deletePost(Long id) {
        if (postRepository.existsById(id)) {
            postRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public Optional<PostDTO> getPostById(Long id) {
        return postRepository.findById(id).map(post -> {
            PostEntity updatedPost = post.copyWithIncrementedViewCount();
            PostEntity savedPost = postRepository.save(updatedPost);
            return enhancePostWithImageUrl(savedPost);
        });
    }

    private PostDTO enhancePostWithImageUrl(PostEntity post) {
        String imageUrl = post.getImagePath();
        if (imageUrl != null && !imageUrl.isEmpty() && !imageUrl.startsWith("/api/images/")) {
            imageUrl = String.format("/api/images/%s", post.getImagePath());
        }
        return new PostDTO.Builder()
                .id(post.getId())
                .title(post.getTitle())
                .content(post.getContent())
                .author(post.getAuthor())
                .imagePath(imageUrl)
                .viewCount(post.getViewCount())
                .build();
    }


    public Optional<PostEntity> getPostEntityById(Long id) {
        return postRepository.findById(id);
    }

    public PostEntity savePost(PostEntity post) {
        return postRepository.save(post);
    }
}
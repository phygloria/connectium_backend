package com.ohgiraffers.crud_back.community.service;

import com.ohgiraffers.crud_back.community.model.dto.CommunityPostDTO;
import com.ohgiraffers.crud_back.community.model.entity.CommunityPost;
import com.ohgiraffers.crud_back.community.repository.CommunityPostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommunityPostService {
    private final CommunityPostRepository repository;

    @Autowired
    public CommunityPostService(CommunityPostRepository repository) {
        this.repository = repository;
    }

    public List<CommunityPostDTO> getAllPosts() {
        return repository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public List<CommunityPostDTO> getPostsByCategory(String category) {
        return repository.findByCategory(category).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public CommunityPostDTO getPost(Long id) {
        return repository.findById(id)
                .map(post -> {
                    CommunityPost updatedPost = post.copyWithIncrementedViewCount();
                    CommunityPost savedPost = repository.save(updatedPost);
                    return convertToDTO(savedPost);
                })
                .orElseThrow(() -> new RuntimeException("Post not found"));
    }

    public CommunityPostDTO createPost(CommunityPostDTO postDTO) {
        CommunityPost post = convertToEntity(postDTO);
        return convertToDTO(repository.save(post));
    }

    public CommunityPostDTO updatePost(Long id, CommunityPostDTO postDTO) {
        CommunityPost post = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Post not found"));

        post.setTitle(postDTO.getTitle());
        post.setAuthorName(postDTO.getAuthorName());
        post.setContent(postDTO.getContent());
        post.setCategory(postDTO.getCategory());
        post.setViewCount(post.getViewCount());

        return convertToDTO(repository.save(post));
    }

    public void deletePost(Long id) {
        repository.deleteById(id);
    }

    private CommunityPostDTO convertToDTO(CommunityPost post) {
        CommunityPostDTO dto = new CommunityPostDTO();
        dto.setId(post.getId());
        dto.setTitle(post.getTitle());
        dto.setAuthorName(post.getAuthorName());
        dto.setContent(post.getContent());
        dto.setCategory(post.getCategory());
        dto.setViewCount(post.getViewCount());
        return dto;
    }

    private CommunityPost convertToEntity(CommunityPostDTO dto) {
        CommunityPost post = new CommunityPost();
        post.setId(dto.getId());
        post.setTitle(dto.getTitle());
        post.setAuthorName(dto.getAuthorName());
        post.setContent(dto.getContent());
        post.setCategory(dto.getCategory());
        post.setViewCount(dto.getViewCount());
        return post;
    }
}
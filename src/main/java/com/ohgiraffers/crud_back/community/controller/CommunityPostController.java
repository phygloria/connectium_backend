package com.ohgiraffers.crud_back.community.controller;

import com.ohgiraffers.crud_back.community.model.dto.CommunityPostDTO;
import com.ohgiraffers.crud_back.community.service.CommunityPostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/community")
public class CommunityPostController {
    private final CommunityPostService service;

    @Autowired
    public CommunityPostController(CommunityPostService service) {
        this.service = service;
    }

    @GetMapping
    public List<CommunityPostDTO> getAllPosts() {
        return service.getAllPosts();
    }

    @GetMapping("/category/{category}")
    public List<CommunityPostDTO> getPostsByCategory(@PathVariable String category) {
        return service.getPostsByCategory(category);
    }

    @GetMapping("/{id}")
    public CommunityPostDTO getPost(@PathVariable Long id) {
        return service.getPost(id);
    }

    @PostMapping
    public CommunityPostDTO createPost(@RequestBody CommunityPostDTO postDTO) {
        return service.createPost(postDTO);
    }

    @PutMapping("/{id}")
    public CommunityPostDTO updatePost(@PathVariable Long id, @RequestBody CommunityPostDTO postDTO) {
        return service.updatePost(id, postDTO);
    }

    @DeleteMapping("/{id}")
    public void deletePost(@PathVariable Long id) {
        service.deletePost(id);
    }
}
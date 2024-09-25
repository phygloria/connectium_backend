package com.ohgiraffers.crud_back.community.controller;

import com.ohgiraffers.crud_back.community.model.dto.CommunityDTO;
import com.ohgiraffers.crud_back.community.service.CommunityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/community")
public class CommunityController {
    private final CommunityService service;

    @Autowired
    public CommunityController(CommunityService service) {
        this.service = service;
    }

    @GetMapping
    public List<CommunityDTO> getAllPosts() {
        return service.getAllPosts();
    }

    @GetMapping("/category/{category}")
    public List<CommunityDTO> getPostsByCategory(@PathVariable String category) {
        return service.getPostsByCategory(category);
    }

    @GetMapping("/{id}")
    public CommunityDTO getPost(@PathVariable Long id) {
        return service.getPost(id);
    }

    @PostMapping
    public CommunityDTO createPost(@RequestBody CommunityDTO postDTO) {
        return service.createPost(postDTO);
    }

    @PutMapping("/{id}")
    public CommunityDTO updatePost(@PathVariable Long id, @RequestBody CommunityDTO postDTO) {
        return service.updatePost(id, postDTO);
    }

    @DeleteMapping("/{id}")
    public void deletePost(@PathVariable Long id) {
        service.deletePost(id);
    }
}
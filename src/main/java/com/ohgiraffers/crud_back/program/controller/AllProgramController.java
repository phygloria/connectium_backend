package com.ohgiraffers.crud_back.program.controller;

import com.ohgiraffers.crud_back.program.model.dto.Program1DTO;
import com.ohgiraffers.crud_back.program.model.dto.Program2DTO;
import com.ohgiraffers.crud_back.program.service.Program1Service;
import com.ohgiraffers.crud_back.program.service.Program2Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/programs")
public class AllProgramController {
    private final Program1Service program1Service;
    private final Program2Service program2Service;
    private final RestTemplate restTemplate;

    @Value("${ftp.server}")
    private String server;

    @Value("${ftp.port}")
    private int port;

    @Value("${ftp.username}")
    private String username;

    @Value("${ftp.password}")
    private String password;

    @Autowired
    public AllProgramController(Program1Service program1Service,
                                Program2Service program2Service,
                                RestTemplate restTemplate) {
        this.program1Service = program1Service;
        this.program2Service = program2Service;
        this.restTemplate = restTemplate;
    }

    @GetMapping("/all")
    public Map<String, List<?>> getCombinedPrograms() {
        Map<String, List<?>> result = new HashMap<>();
        List<Program1DTO> culPrograms = program1Service.getCulProgram();
        List<Program2DTO> eduPrograms = program2Service.getEduProgram();
        result.put("CulProgram", culPrograms);
        result.put("EduProgram", eduPrograms);
        return result;
    }

    @GetMapping("/proxy-image")
    public ResponseEntity<byte[]> proxyImage(@RequestParam String filename, @RequestParam(required = false) String type) {
        try {
            String imageUrl = String.format("ftp://%s:%d/HOMEPAGE/PROGRAM/IN/%s", server, port, filename);
            // For authentication, you may need to set headers or use a different method
            ResponseEntity<byte[]> response = restTemplate.getForEntity(imageUrl, byte[].class);
            return ResponseEntity.ok()
                    .contentType(MediaType.IMAGE_JPEG) // Adjust if necessary
                    .body(response.getBody());
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/detail/{type}/{svcid}")
    public ResponseEntity<?> getProgramDetail(@PathVariable String type, @PathVariable String svcid) {
        if ("CulProgram".equals(type)) {
            Program1DTO detail = program1Service.getProgram1Detail(svcid);
            return ResponseEntity.ok(detail);
        } else if ("EduProgram".equals(type)) {
            Program2DTO detail = program2Service.getProgram2Detail(svcid);
            return ResponseEntity.ok(detail);
        } else {
            return ResponseEntity.badRequest().body("Invalid program type");
        }
    }
}
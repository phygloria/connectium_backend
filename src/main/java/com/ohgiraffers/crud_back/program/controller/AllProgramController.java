package com.ohgiraffers.crud_back.program.controller;

import com.ohgiraffers.crud_back.program.model.dto.Program1DTO;
import com.ohgiraffers.crud_back.program.model.dto.Program2DTO;
import com.ohgiraffers.crud_back.program.service.Program1Service;
import com.ohgiraffers.crud_back.program.service.Program2Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
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
    private String ftpServerUrl;

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
            String imageUrl;
            if ("program1".equals(type)) {
                // Program1의 경우 FTP 서버 URL을 사용
                imageUrl = ftpServerUrl + "/HOMEPAGE/PROGRAM/IN/" + filename;
            } else {
                // Program2 또는 기타의 경우 제공된 URL을 그대로 사용
                imageUrl = filename;
            }

            ResponseEntity<byte[]> response = restTemplate.getForEntity(imageUrl, byte[].class);
            return ResponseEntity.ok()
                    .contentType(MediaType.IMAGE_JPEG) // 또는 적절한 콘텐츠 타입
                    .body(response.getBody());
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
}
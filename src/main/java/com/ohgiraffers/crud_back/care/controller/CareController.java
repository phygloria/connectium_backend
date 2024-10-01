package com.ohgiraffers.crud_back.care.controller;

import com.ohgiraffers.crud_back.care.model.dto.CareDTO;
import com.ohgiraffers.crud_back.care.service.CareService;
import com.ohgiraffers.crud_back.education.model.dto.EducationDTO;
import com.ohgiraffers.crud_back.education.service.EducationService;
import org.apache.commons.net.ftp.FTPClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@RestController
@RequestMapping("/api")
public class CareController {

    @Value("${ftp.server}")
    private String FTP_SERVER;
    @Value("${ftp.port}")
    private int FTP_PORT;
    @Value("${ftp.username}")
    private String FTP_USER;
    @Value("${ftp.password}")
    private String FTP_PASSWORD;

    @Autowired
    private CareService careService;

    // 전체목록
    @GetMapping("/care")
    public ResponseEntity<List<CareDTO>> getAllCares() {
        return ResponseEntity.ok(careService.getAllCares());
    }

    @GetMapping("/care/{id}")
    public ResponseEntity<CareDTO> getAllCareById(@PathVariable Long id) {
        return careService.getCareById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/images/Care service/{imageName}")
    public ResponseEntity<byte[]> getImage(@PathVariable String imageName) throws IOException {
        FTPClient ftpClient = new FTPClient();

        try {
            ftpClient.connect(FTP_SERVER, FTP_PORT);
            ftpClient.login(FTP_USER, FTP_PASSWORD);
            ftpClient.enterLocalPassiveMode();

            String fullPath = "/HOMEPAGE/Care service/" + imageName;
            try (InputStream inputStream = ftpClient.retrieveFileStream(fullPath)) {
                if (inputStream == null) {
                    return ResponseEntity.notFound().build();
                }
                ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                byte[] buffer = new byte[4096];
                int bytesRead;
                while ((bytesRead = inputStream.read(buffer)) != -1) {
                    outputStream.write(buffer, 0, bytesRead);
                }
                byte[] imageBytes = outputStream.toByteArray();
                return ResponseEntity.ok()
                        .contentType(MediaType.IMAGE_JPEG)
                        .body(imageBytes);
            }
        } finally {
            if (ftpClient.isConnected()) {
                ftpClient.logout();
                ftpClient.disconnect();
            }
        }
    }

}

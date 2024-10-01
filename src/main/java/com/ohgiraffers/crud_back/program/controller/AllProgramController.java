package com.ohgiraffers.crud_back.program.controller;

import com.ohgiraffers.crud_back.program.model.dto.Program1DTO;
import com.ohgiraffers.crud_back.program.model.dto.Program2DTO;
import com.ohgiraffers.crud_back.program.service.Program1Service;
import com.ohgiraffers.crud_back.program.service.Program2Service;
import org.apache.commons.io.IOUtils;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/programs")
public class AllProgramController {
    private final Program1Service program1Service;
    private final Program2Service program2Service;

    @Value("${ftp.server}")
    private String ftpServer;

    @Value("${ftp.port}")
    private int ftpPort;

    @Value("${ftp.username}")
    private String ftpUsername;

    @Value("${ftp.password}")
    private String ftpPassword;

    @Value("${ftp.in-path}")
    private String ftpInPath;

    private final Logger logger = LoggerFactory.getLogger(AllProgramController.class);


    @Autowired
    public AllProgramController(Program1Service program1Service,
                                Program2Service program2Service) {
        this.program1Service = program1Service;
        this.program2Service = program2Service;
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
    public ResponseEntity<byte[]> getProxyImage(@RequestParam String filename) {
        if (!isValidFilename(filename)) {
            logger.warn("Invalid filename requested: {}", filename);
            return ResponseEntity.badRequest().build();
        }

        FTPClient ftpClient = new FTPClient();
        try {
            ftpClient.connect(ftpServer, ftpPort);
            ftpClient.login(ftpUsername, ftpPassword);
            ftpClient.enterLocalPassiveMode();
            ftpClient.setFileType(FTP.BINARY_FILE_TYPE);

            String[] paths = {"/HOMEPAGE/PROGRAM/IN/"};
            InputStream inputStream = null;

            for (String path : paths) {
                String remoteFilePath = path + filename;
                inputStream = ftpClient.retrieveFileStream(remoteFilePath);
                if (inputStream != null) {
                    break;
                }
            }

            if (inputStream == null) {
                logger.warn("File not found on FTP server: {}", filename);
                return ResponseEntity.notFound().build();
            }

            byte[] imageBytes = IOUtils.toByteArray(inputStream);
            inputStream.close();

            MediaType mediaType = getMediaTypeForFilename(filename);

            return ResponseEntity.ok()
                    .contentType(mediaType)
                    .body(imageBytes);
        } catch (IOException e) {
            logger.error("Error retrieving image from FTP: {}", filename, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        } finally {
            try {
                if (ftpClient.isConnected()) {
                    ftpClient.logout();
                    ftpClient.disconnect();
                }
            } catch (IOException ex) {
                logger.error("Error disconnecting from FTP server", ex);
            }
        }
    }

    public boolean isValidFilename(String filename) {
        if (filename == null || filename.isEmpty()) {
            return false;
        }

        // 파일 이름에 허용되지 않는 문자(예: <, >, :, ", \, |, ?, *)가 포함되어 있는지 확인
        String invalidChars = "<>:\"|?*";
        for (char c : invalidChars.toCharArray()) {
            if (filename.indexOf(c) >= 0) {
                return false;
            }
        }
        return true;
    }

    public MediaType getMediaTypeForFilename(String filename) {
        if (filename == null) {
            return MediaType.APPLICATION_OCTET_STREAM; // 기본값
        }

        String extension = filename.substring(filename.lastIndexOf('.') + 1).toLowerCase();
        switch (extension) {
            case "jpg":
            case "jpeg":
                return MediaType.IMAGE_JPEG;
            case "png":
                return MediaType.IMAGE_PNG;
            case "gif":
                return MediaType.IMAGE_GIF;
            case "pdf":
                return MediaType.APPLICATION_PDF;
            default:
                return MediaType.APPLICATION_OCTET_STREAM;
        }
    }



    @GetMapping("/detail/{type}/{svcid}")
    public ResponseEntity<?> getProgramDetail(@PathVariable String type, @PathVariable String svcid) {
        try {
            if ("CulProgram".equals(type)) {
                Program1DTO detail = program1Service.getProgram1Detail(svcid);
                if (detail == null) {
                    return ResponseEntity.notFound().build();
                }
                return ResponseEntity.ok(detail);
            } else if ("EduProgram".equals(type)) {
                Program2DTO detail = program2Service.getProgram2Detail(svcid);
                if (detail == null) {
                    return ResponseEntity.notFound().build();
                }
                return ResponseEntity.ok(detail);
            } else {
                return ResponseEntity.badRequest().body("Invalid program type");
            }
        } catch (Exception e) {
            logger.error("Error retrieving program detail: type={}, svcid={}", type, svcid, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while retrieving program details");
        }
    }
}
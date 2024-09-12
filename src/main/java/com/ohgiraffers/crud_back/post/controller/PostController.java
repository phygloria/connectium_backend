package com.ohgiraffers.crud_back.post.controller;

import com.ohgiraffers.crud_back.post.model.dto.PostDTO;
import com.ohgiraffers.crud_back.post.model.entity.PostEntity;
import com.ohgiraffers.crud_back.post.service.PostService;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@RestController
@RequestMapping("/api")  // 여기를 "/api"로 수정
@CrossOrigin(origins = "http://localhost:8081", allowCredentials = "true")
public class PostController {

    @Value("${ftp.server}")
    private String FTP_SERVER;
    @Value("${ftp.port}")
    private int FTP_PORT;
    @Value("${ftp.username}")
    private String FTP_USER;
    @Value("${ftp.password}")
    private String FTP_PASSWORD;

    private final PostService postService;

    @Autowired
    public PostController(PostService postService) {
        this.postService = postService;
    }

    @GetMapping("/post")
    public ResponseEntity<List<PostDTO>> getAllPosts() {
        return ResponseEntity.ok(postService.getAllPosts());
    }

    @PostMapping("/post")
    public ResponseEntity<PostEntity> createPost(
            @RequestParam("title") String title,
            @RequestParam("content") String content,
            @RequestParam("author") String author,
            @RequestParam(value = "image", required = false) MultipartFile image) {
        // required = false 덕분에 이미지 파일을 선택적으로 받을 수 있음
        // 사용자가 사진을 첨부하지 않고 요청을 보내면, image 의 값은 null
        // 기본 DTO 생성 (이미지 경로는 아직 설정되지 않음)
        PostDTO.Builder builder = new PostDTO.Builder()
                .title(title)
                .content(content)
                .author(author);

        // 이미지 파일이 있는 경우 처리
        if (image != null && !image.isEmpty()) {
            String fileName = System.currentTimeMillis() + "_" + image.getOriginalFilename();
            try {
                boolean uploaded = uploadFileToFTP(image, fileName);
                if (uploaded) {
                    builder.imagePath(fileName);
                }
            } catch (IOException e) {
                e.printStackTrace();
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
            }
        }

        // 최종 DTO 객체 생성
        PostDTO postDTO = builder.build();

        // @service 로 넘김.
        postService.createPost(postDTO);
        return ResponseEntity.status(HttpStatus.CREATED).build();

    }

    private boolean uploadFileToFTP(MultipartFile file, String fileName) throws IOException {

        FTPClient ftpClient = new FTPClient();
        try {
            ftpClient.connect(FTP_SERVER, FTP_PORT);
            boolean login = ftpClient.login(FTP_USER, FTP_PASSWORD);
            if (!login) {
                throw new IOException("FTP 서버 로그인 실패");
            }
            ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
            ftpClient.enterLocalPassiveMode(); // 이 줄 추가
            boolean stored = ftpClient.storeFile(fileName, file.getInputStream());
            if (!stored) {
                throw new IOException("FTP 서버에 파일 업로드 실패");
            }
            return true;
        } catch (IOException e) {
            throw new IOException("FTP 업로드 중 오류 발생: " + e.getMessage(), e);
        } finally {
            if (ftpClient.isConnected()) {
                ftpClient.logout();
                ftpClient.disconnect();
            }
        }
    }

    @GetMapping("/post/{id}")
    public ResponseEntity<PostDTO> getPostById(@PathVariable Long id) {
        return postService.getPostById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/images/{imageName}")
    public ResponseEntity<byte[]> getImage(@PathVariable String imageName) throws IOException {

        FTPClient ftpClient = new FTPClient();

        try {
            ftpClient.connect(FTP_SERVER, FTP_PORT);
            ftpClient.login(FTP_USER, FTP_PASSWORD);
            ftpClient.enterLocalPassiveMode();

            try (InputStream inputStream = ftpClient.retrieveFileStream(imageName)) {
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



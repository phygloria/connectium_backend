package com.ohgiraffers.crud_back.program.service;

import org.apache.commons.net.ftp.FTPClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

@Service
public class FtpService {

    @Value("${ftp.server}")
    private String server;

    @Value("${ftp.port}")
    private int port;

    @Value("${ftp.username}")
    private String username;

    @Value("${ftp.password}")
    private String password;

    public void uploadImage(String localFilePath, String remoteFileName) {
        FTPClient ftpClient = new FTPClient();
        try {
            ftpClient.connect(server, port);
            ftpClient.login(username, password);
            ftpClient.enterLocalPassiveMode();

            String remoteFilePath = "/HOMEPAGE/PROGRAM/IN/" + remoteFileName;
            File localFile = new File(localFilePath);

            try (FileInputStream inputStream = new FileInputStream(localFile)) {
                boolean done = ftpClient.storeFile(remoteFilePath, inputStream);
                if (done) {
                    System.out.println("Image uploaded successfully.");
                } else {
                    System.out.println("Failed to upload image.");
                }
            }

        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        } finally {
            try {
                if (ftpClient.isConnected()) {
                    ftpClient.logout();
                    ftpClient.disconnect();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
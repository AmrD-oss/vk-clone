package com.example.socialnetwork.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Service
public class FileService {

    @Value("${application.avatar-folder}")
    private String avatarFolder;

    public String uploadAvatar(MultipartFile avatar) {
        try {
            checkExistenceDirectory(avatarFolder);
        } catch (IOException e) {
            e.printStackTrace();
        }

        Path directory = Paths.get(avatarFolder);

        try {
            byte[] avatarByteSize = avatar.getBytes();

            if(avatarByteSize.length != 0 && checkFileExtension(avatar)) {
                Files.write(Paths.get(directory + File.separator + avatar.getOriginalFilename()), avatarByteSize);
            }

            return avatar.getOriginalFilename();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    private void checkExistenceDirectory(String directory) throws IOException {
        Path imgDirectory = Paths.get(directory);

        if(!Files.exists(imgDirectory)) {
            Files.createDirectory(imgDirectory);
        }
    }

    private boolean checkFileExtension(MultipartFile file) {
        String filename = file.getOriginalFilename();
        return filename.endsWith(".jpg") || filename.endsWith(".png");
    }
}

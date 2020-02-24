package com.example.socialnetwork.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.UUID;

@Service
public class FileService {

    @Value("${application.avatar-folder}")
    private String avatarFolder;

    public void downloadAvatar(MultipartFile avatar) throws IOException {
        File folderImage = new File(avatarFolder);

        if(!folderImage.exists()) {
            folderImage.mkdir();
        }

        if(avatar != null) {
            String imgName = avatar.getOriginalFilename();
            String newAvatar = avatarFolder + imgName;

            avatar.transferTo(new File(newAvatar));
        }
    }
}

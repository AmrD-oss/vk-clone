package com.example.socialnetwork.service;

import com.example.socialnetwork.models.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
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

    private final UserService userService;

    @Autowired
    public FileService(UserService userService) {
        this.userService = userService;
    }

    public String uploadImage(MultipartFile img, Path directory) {
        try {
            checkExistenceDirectory(directory.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            byte[] imgSize = img.getBytes();

            if(imgSize.length != 0 && checkFileExtension(img)) {
                Files.write(Paths.get(directory + File.separator + img.getOriginalFilename()), imgSize);
            }

            return img.getOriginalFilename();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    public void setDefaultAvatar() {
        UserEntity currentUser = userService.getAnAuthorizedUser();
        if(currentUser.getAvatar() == null || currentUser.getAvatar().equals(""))
            userService.updateAvatarOfCurrentUser("default_avatar.png");
    }

    public void setDefaultCover() {
        UserEntity currentUser = userService.getAnAuthorizedUser();
        if(currentUser.getCover() == null || currentUser.getCover().equals(""))
            userService.updateCoverOfCurrentUser("default_cover.jpg");
    }

    public void deleteAvatar() {
        userService.updateAvatarOfCurrentUser("default_avatar.png");
    }

    private void checkExistenceDirectory(String directory) throws IOException {
        Path imgDirectory = Paths.get(directory);

        if(!Files.exists(imgDirectory)) {
            Files.createDirectory(imgDirectory);
        }
    }

    private boolean checkFileExtension(MultipartFile file) {
        return  file.getOriginalFilename().endsWith(".jpg") ||
                file.getOriginalFilename().endsWith(".png") ||
                file.getOriginalFilename().endsWith(".gif");
    }
}

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

    private void checkExistenceDirectory(String directory) throws IOException {
        Path imgDirectory = Paths.get(directory);

        if(!Files.exists(imgDirectory)) {
            Files.createDirectory(imgDirectory);
        }
    }

    private boolean checkFileExtension(MultipartFile file) {
        return file.getOriginalFilename().endsWith(".jpg")
                || file.getOriginalFilename().endsWith(".png")
                || file.getOriginalFilename().endsWith(".gif");
    }
}

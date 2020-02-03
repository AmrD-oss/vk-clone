package com.example.socialnetwork.controllers;

import com.example.socialnetwork.service.FileService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.UUID;


@Slf4j
@Controller
@RequestMapping("/my_page")
public class MyPageController {

    private FileService fileService;

    @Autowired
    public MyPageController(FileService fileService) {
        this.fileService = fileService;
    }

    @GetMapping
    public String showMyPage(Model model) {
        log.info("showMyPage method called");

        return "my_page";
    }

    @GetMapping("/download_avatar_form")
    public String downloadAvatarForm() {
        return "download_avatar_form";
    }

    @ResponseBody
    @PostMapping("/download_avatar_form")
    public String submitDownloadAvatarForm(@RequestParam("file") MultipartFile multipartFile, Model model) {
//
//        String fileName = multipartFile.getOriginalFilename();
//        String toStringFile = multipartFile.toString();
//        String rootPath = toStringFile.substring(0, toStringFile.lastIndexOf("\\")) + "\\";
//        String extensionFile = toStringFile.substring(toStringFile.lastIndexOf("."));
//        String uuid = UUID.randomUUID().toString();
//
//        if (!multipartFile.isEmpty()) {
//            if (multipartFile.toString().endsWith(".jpg") || multipartFile.toString().endsWith(".png")) {
//                try {
//                    byte[] buff = multipartFile.getBytes();
//
//                    BufferedOutputStream outputStream = new BufferedOutputStream(new FileOutputStream(rootPath + uuid + extensionFile));
//                    outputStream.write(buff);
//                    outputStream.flush();
//                    outputStream.close();
//
//                    fileService.saveFile(multipartFile);
//
//                    model.addAttribute("avatar", multipartFile);
//
//                    return "redirect:/my_page";
//
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//            else {
//                return "File " + fileName + " an unsupported extension" + ". Select a file with the extension jpg or png";
//            }
//        } else {
//            return "File " + multipartFile.getName() + " is empty!";
//        }
//
        return "File not found!";
    }

    @GetMapping("/edit_page")
    public String editInfoBlock() {
        return "edit_page";
    }
}

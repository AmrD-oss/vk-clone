package com.example.socialnetwork.controllers;

import com.example.socialnetwork.models.UserEntity;
import com.example.socialnetwork.service.FileService;
import com.example.socialnetwork.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Paths;
import java.time.LocalDate;

@Slf4j
@Controller
@RequestMapping("/my_page")
public class MyPageController {

    @Value("${application.avatar-folder}")
    private String avatarFolder;

    @Value("${application.cover-folder}")
    private String coverFolder;

    private UserService userService;
    private FileService fileService;

    @Autowired
    public MyPageController(UserService userService, FileService fileService) {
        this.userService = userService;
        this.fileService = fileService;
    }

    @GetMapping
    public String showMyPage(Model model) {
        log.info("showMyPage method called");

//        Map<String, Valute> valutes = userService.getAllValutes();
//        for(Map.Entry<String, Valute> valute : valutes.entrySet()) {
//            String name = valute.getKey();
//
//            if(name.equals("USD")) {
//                model.addAttribute("USD", Math.round(valute.getValue().getValue()));
//            }
//
//            if(name.equals("EUR")) {
//                model.addAttribute("EUR", Math.round(valute.getValue().getValue()));
//            }
//        }

        UserEntity currentUser = userService.getAnAuthorizedUser();

        model.addAttribute("id", currentUser.getId());
        model.addAttribute("username", currentUser.getUsername());
        model.addAttribute("name", currentUser.getName());
        model.addAttribute("surname", currentUser.getSurname());

        fileService.setDefaultAvatar();
        model.addAttribute("avatar", currentUser.getAvatar());

        fileService.setDefaultCover();
        model.addAttribute("cover", currentUser.getCover());

        model.addAttribute("status", currentUser.getStatus());
        model.addAttribute("email", currentUser.getEmail());
        model.addAttribute("birthday", currentUser.getDateOfBirth());
        model.addAttribute("city", currentUser.getCity());
        model.addAttribute("online", currentUser.getOnline());

        return "my_page";
    }

    @GetMapping("/upload_avatar_form")
    public String getUploadAvatarForm(Model model) {
        log.info("getUploadAvatarForm method called");
        model.addAttribute("avatar", userService.getAnAuthorizedUser().getAvatar());
        return "upload_avatar_form";
    }

    @PostMapping("/upload_avatar_form")
    public String submitUploadAvatarForm(@RequestParam MultipartFile avatar) {
        log.info("submitUploadAvatarForm method called");

        fileService.uploadImage(avatar, Paths.get(avatarFolder));
        userService.updateAvatarOfCurrentUser(avatar.getOriginalFilename());

        log.info("Avatar: " + avatar.getOriginalFilename() + " uploaded successfully");
        return "redirect:/my_page";
    }

    @GetMapping("/edit_page")
    public String editInfoForm(Model model) {
        log.info("editInfoForm method called");
        return "edit_page";
    }

    @PostMapping("/edit_page")
    public String editInfoFormSubmit(@RequestParam String name, @RequestParam String surname,
                                     @RequestParam String email, @RequestParam String status,
                                     @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate birthday,
                                     @RequestParam String city) {
        log.info("editInfoFormSubmit method called");
        userService.updateUser(name,surname,email,status,birthday,city);
        log.info("Сurrent user updated page data: " + userService.getAnAuthorizedUser().toString());
        return "redirect:/my_page";
    }

    @GetMapping("/upload_cover_form")
    public String getUploadCoverForm(Model model) {
        log.info("getUploadCoverForm method called");
        return "upload_cover_form";
    }

    @PostMapping("/upload_cover_form")
    public String submitUploadCoverForm(@RequestParam MultipartFile cover, Model model) {
        log.info(userService.getAnAuthorizedUser().getUsername() + " confirmed background change");

        fileService.uploadImage(cover, Paths.get(coverFolder));
        userService.updateCoverOfCurrentUser(cover.getOriginalFilename());

        return "redirect:/my_page";
    }

    @PostMapping("/delete_avatar")
    public String deleteAvatar() {
        log.info("User " + userService.getAnAuthorizedUser().getUsername() + " deleted avatar");
        fileService.deleteAvatar();
        return "redirect:/my_page";
    }
}
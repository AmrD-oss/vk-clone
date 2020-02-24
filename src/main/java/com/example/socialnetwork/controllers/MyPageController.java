package com.example.socialnetwork.controllers;

import com.example.socialnetwork.models.UserEntity;
import com.example.socialnetwork.models.Valute;
import com.example.socialnetwork.service.FileService;
import com.example.socialnetwork.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

@Slf4j
@Controller
@RequestMapping("/my_page")
public class MyPageController {

    @Value("${application.avatar-folder}")
    private String avatarFolder;

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

        Map<String, Valute> valutes = userService.getAllValutes();
        for(Map.Entry<String, Valute> valute : valutes.entrySet()) {
            String name = valute.getKey();

            if(name.equals("USD")) {
                model.addAttribute("USD", Math.round(valute.getValue().getValue()));
            }

            if(name.equals("EUR")) {
                model.addAttribute("EUR", Math.round(valute.getValue().getValue()));
            }
        }

        UserEntity currentUser = userService.getAnAuthorizedUser();
        currentUser.setOnline(true);

        model.addAttribute("username", currentUser.getUsername());
        model.addAttribute("name", currentUser.getName());
        model.addAttribute("surname", currentUser.getSurname());
        model.addAttribute("avatar", currentUser.getAvatar());
        model.addAttribute("status", currentUser.getStatus());
        model.addAttribute("email", currentUser.getEmail());
        model.addAttribute("birthday", currentUser.getDateOfBirth());
        model.addAttribute("city", currentUser.getCity());
        model.addAttribute("online", currentUser.getOnline());

        return "my_page";
    }

    @GetMapping("/download_avatar_form")
    public String downloadAvatarForm(Model model) {
        log.info("downloadAvatarForm method called");

        UserEntity currentUser = userService.getAnAuthorizedUser();
        model.addAttribute("currentUser", currentUser);

        return "download_avatar_form";
    }

    @PostMapping("/download_avatar_form")
    public String submitDownloadAvatarForm(@RequestParam("avatar") MultipartFile avatar,
                                           @ModelAttribute UserEntity currentUser,
                                           Model model) {

        currentUser.setAvatar(avatar.getOriginalFilename());
        userService.saveUser(currentUser);

        return "redirect:/my_page";
    }

    @GetMapping("/edit_page")
    public String editInfoBlock(Model model) {
        return "edit_page";
    }

    @PostMapping("/edit_page")
    public String changeInfoBlockSubmit(Model model, BindingResult bindingResult) {
        if(bindingResult.hasErrors()){
            return "edit_page";
        }

        UserEntity userEntity = userService.getAnAuthorizedUser();
        userService.updateUser(userEntity);

        model.addAttribute("updated_name", userEntity.getName());
        model.addAttribute("updated_surname", userEntity.getSurname());
        model.addAttribute("updated_status", userEntity.getStatus());
        model.addAttribute("updated_birthday", userEntity.getDateOfBirth());
        model.addAttribute("updated_city", userEntity.getCity());

        return "redirect:/my_page";
    }

}

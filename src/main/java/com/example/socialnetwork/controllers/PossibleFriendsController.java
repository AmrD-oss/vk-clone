package com.example.socialnetwork.controllers;

import com.example.socialnetwork.models.UserEntity;
import com.example.socialnetwork.service.FileService;
import com.example.socialnetwork.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Slf4j
@Controller
public class PossibleFriendsController {

    private final UserService userService;
    private final FileService fileService;

    @Autowired
    public PossibleFriendsController(UserService userService, FileService fileService) {
        this.userService = userService;
        this.fileService = fileService;
    }

    @GetMapping("/possible_friends")
    public String showPossibleFriends(Model model) {
        log.info("showPossibleFriends method called");

        List<UserEntity> possibleFriends = userService.getAllUsers();
        possibleFriends.remove(userService.getAnAuthorizedUser());

        model.addAttribute("possibleFriends", possibleFriends);
        model.addAttribute("avatar", userService.getAnAuthorizedUser().getAvatar());
        model.addAttribute("username", userService.getAnAuthorizedUser().getUsername());

        return "possible_friends";
    }
}

package com.example.socialnetwork.controllers;

import com.example.socialnetwork.models.UserEntity;
import com.example.socialnetwork.service.SecurityService;
import com.example.socialnetwork.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
public class RegistrationController {

    private final UserService userService;
    private final SecurityService securityService;

    @Autowired
    public RegistrationController(UserService userService, SecurityService securityService) {
        this.userService = userService;
        this.securityService = securityService;
    }

    @GetMapping("/registration")
    public String showRegistrationForm(Model model) {
        model.addAttribute("userEntity", new UserEntity());

        return "registration";
    }

    @PostMapping("/registration")
    public String registerUserAccount(@ModelAttribute("userEntity") @Valid UserEntity account,
                                      BindingResult bindingResult, Model model) {

        if(bindingResult.hasErrors()) {
            return "registration";
        }

        if(!account.getPassword().equals(account.getMatchingPassword())) {
            model.addAttribute("passwordError", "Пароли не совпадают!");

            return "registration";
        }

        if(!userService.saveUser(account)) {
            model.addAttribute("usernameError", "Пользователь с таким именем уже существует!");

            return "registration";
        }

        userService.saveUser(account);
        securityService.autoLogin(account.getUsername(), account.getMatchingPassword());

        return "redirect:/login";
    }
}

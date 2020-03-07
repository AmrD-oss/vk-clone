package com.example.socialnetwork.controllers;

import com.example.socialnetwork.models.UserEntity;
import com.example.socialnetwork.service.SecurityService;
import com.example.socialnetwork.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Slf4j
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
        log.info("showRegistrationForm method called");
        model.addAttribute("newUser", new UserEntity());
        return "registration";
    }

    @PostMapping("/registration")
    public String registerUserAccount(@ModelAttribute("newUser") @Valid UserEntity account,
                                      BindingResult bindingResult,
                                      Model model) {
        log.info("registerUserAccount method called");

        if(bindingResult.hasErrors()) {
            log.error("Validation errors: " + bindingResult.hasErrors());
            return "registration";
        }

        if(!account.getPassword().equals(account.getMatchingPassword())) {
            log.error("Passwords do not match: " + account.getPassword() + " != " + account.getMatchingPassword());
            model.addAttribute("passwordError", "Пароли не совпадают!");
            return "registration";
        }

        if(!userService.saveUser(account)) {
            log.error("A user with the same name already exists");
            model.addAttribute("usernameError", "Пользователь с таким именем уже существует!");

            return "registration";
        }

        userService.saveUser(account);
        securityService.autoLogin(account.getUsername(), account.getMatchingPassword());

        log.info("New user registered: " + account.toString());

        return "redirect:/login";
    }
}

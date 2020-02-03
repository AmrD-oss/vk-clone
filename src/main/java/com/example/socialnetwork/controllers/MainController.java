package com.example.socialnetwork.controllers;


import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Slf4j
@Controller
@RequestMapping("/")
public class MainController {

    @GetMapping
    public String showWelcomePage(Model model){
        log.info("showWelcomePage method call");

        return "welcome_page";
    }


}

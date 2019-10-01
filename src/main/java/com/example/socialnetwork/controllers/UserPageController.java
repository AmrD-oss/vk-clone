package com.example.socialnetwork.controllers;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class UserPageController {

    @RequestMapping("/")
    public String showHomePage(){
        return "page";
    }
}

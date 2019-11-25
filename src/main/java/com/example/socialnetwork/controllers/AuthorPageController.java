package com.example.socialnetwork.controllers;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("author")
public class AuthorPageController {

    @RequestMapping("/")
    public String showUserPage(){
        return "page";
    }
}

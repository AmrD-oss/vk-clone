package com.example.socialnetwork.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


@Slf4j
@Controller
@RequestMapping("/myPage")
public class MyPageController {

    @RequestMapping(method = RequestMethod.GET)
    public String showMyPage(Model model) {
        log.info("showMyPage method called");

        return "my_page";
    }

    @RequestMapping()
    public String downloadAvatar() {
        return null;
    }
}

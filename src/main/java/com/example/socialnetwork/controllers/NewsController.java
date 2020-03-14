package com.example.socialnetwork.controllers;

import com.example.socialnetwork.models.News;
import com.example.socialnetwork.models.UserEntity;
import com.example.socialnetwork.service.NewsService;
import com.example.socialnetwork.service.SecurityService;
import com.example.socialnetwork.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;


@Slf4j
@Controller
@RequestMapping("/news")
public class NewsController {

    private NewsService newsService;
    private UserService userService;

    @Autowired
    public NewsController(NewsService newsService, UserService userService) {
        this.newsService = newsService;
        this.userService = userService;
    }

    @GetMapping
    public String showNewsWall(Model model){
        log.info("showNewsWall method called");

        List<News> newsList = null;
        try {
            newsList = newsService.parseAllNews();
        } catch (IOException e) {
            e.printStackTrace();
        }
        UserEntity currentUser = userService.getAnAuthorizedUser();

        model.addAttribute("newsList", newsList);
        model.addAttribute("avatar", currentUser.getAvatar());
        model.addAttribute("username", currentUser.getUsername());

        return "news";
    }
}

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
        List<News> newsList = newsService.getAllNews();

        model.addAttribute("newsList", newsList);
        
        return "news";
    }


    @GetMapping("/create_news_form")
    public String createNewsForm(Model model) {
        log.info("createNews method called");

        UserEntity currentUser = userService.getAnAuthorizedUser();

        model.addAttribute("author_name", currentUser.getName());
        model.addAttribute("author_surname", currentUser.getSurname());
        model.addAttribute("news", new News());

        return "create_news_form";
    }

    @PostMapping("/create_news_form")
    public String submitNews(@ModelAttribute News news, BindingResult result, Model model) {
        log.info("submitNews method called");

        if(result.hasErrors()){
            return "create_news_form";
        }

        UserEntity currentUser = userService.getAnAuthorizedUser();

        news.setUserEntity(currentUser);
        newsService.saveNews(news);

        return "redirect:/news";
    }

    @DeleteMapping("/news/{id}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void deleteNews(@PathVariable Long id) {
        log.info("deleteNews method called");
        newsService.deleteNews(id);
    }
}

package com.example.socialnetwork.controllers;

import com.example.socialnetwork.models.News;
import com.example.socialnetwork.service.NewsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;


@Slf4j
@Controller
@RequestMapping("/news")
public class NewsController {

    private NewsService newsService;

    @Autowired
    public NewsController(NewsService newsService) {
        this.newsService = newsService;
    }

    @GetMapping
    public String showNewsWall(Model model){
        log.info("showNewsWall method called");
        List<News> newsList = newsService.getAllNews();
        model.addAttribute("newsList", newsList);
        
        return "news";
    }

//    @RequestMapping(value = "/{title}/{userEntity}", method = RequestMethod.GET)
//    public News showNews(@PathVariable String title, @PathVariable String userEntity) {
//        log.info("showNews method called");
//        return newsService.getByTitleAndAuthorName(title, userEntity);
//    }

    @GetMapping("/create_news_form")
    public String createNewsForm(Model model) {
        log.info("createNews method called");
        model.addAttribute("news", new News());

        return "create_news_form";
    }

    @PostMapping("/create_news_form")
    public String submitNews(@ModelAttribute News news, BindingResult result, Model model) {
        log.info("submitNews method called");

        if(result.hasErrors()){
            return "create_news_form";
        }

        model.addAttribute("news", news);
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

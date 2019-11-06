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


@Slf4j
@Controller
@RequestMapping("/news")
public class NewsController {

    private final NewsService newsService;

    @Autowired
    public NewsController(NewsService newsService) {
        this.newsService = newsService;
    }

    @RequestMapping(method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public String showNewsWall(Model model){
        log.info("showNewsWall method called");
        model.addAttribute("allNews", newsService.getAllNews());

        return "news";
    }

    @RequestMapping(value = "/{title}/{author}", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public News showNews(@PathVariable String title, @PathVariable String author) {
        log.info("showNews method called");
        return newsService.getByTitleAndAuthorName(title, author);
    }

    @RequestMapping(value = "/create_news", method = RequestMethod.GET)
    public String createNews(Model model) {
        log.info("createNews method called");
        model.addAttribute("news", new News());

        return "create_news";
    }

    @RequestMapping(value = "/create_news/submit", method = RequestMethod.POST)
    public String submitNews(@ModelAttribute @Valid News news, BindingResult result) {
        log.info("submitNews method called");

        if(result.hasErrors()){
            return "create_news";
        }

        newsService.saveNews(news);
        return "redirect:/news";
    }

    @RequestMapping(value = "/news/{id}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void deleteNews(@PathVariable Long id) {
        log.info("deleteNews method called");
        newsService.deleteNews(id);
    }
}

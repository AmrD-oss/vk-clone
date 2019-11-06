package com.example.socialnetwork.service;

import com.example.socialnetwork.models.News;
import com.example.socialnetwork.repositories.NewsRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class NewsService {

    private NewsRepo newsRepo;

    @Autowired
    public NewsService(NewsRepo newsRepo) {
        this.newsRepo = newsRepo;
    }

    public List<News> getAllNews() {
        return newsRepo.findAll();
    }

    public News getByTitleAndAuthorName(String title, @Nullable String authorName) {
        if (title == null) {
            throw new NullPointerException("News title not found");
        }

        News news = null;

        try {
            news = newsRepo.findByTitleAndAuthor_Name(title, authorName);
        } catch (NullPointerException e) {
            e.printStackTrace();
        }

        return news;
    }

    public News saveNews(News news) {
        if (news == null) {
            throw new NullPointerException();
        }

        News newNews = new News();

        newNews.setTitle(news.getTitle());
        newNews.setAuthor(news.getAuthor());
        newNews.setDescription(news.getDescription());
        newNews.setDateOfCreation(news.getDateOfCreation());

        try {
            newsRepo.save(newNews);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return newNews;
    }

    public void deleteNews(Long id) {
        try {
            newsRepo.deleteById(id);
        } catch (NoSuchElementException e){
            e.printStackTrace();
        }
    }
}

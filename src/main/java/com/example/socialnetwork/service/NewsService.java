package com.example.socialnetwork.service;

import com.example.socialnetwork.models.News;
import com.example.socialnetwork.repositories.NewsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class NewsService {

    private NewsRepository newsRepository;

    @Autowired
    public NewsService(NewsRepository newsRepository) {
        this.newsRepository = newsRepository;
    }

    public List<News> getAllNews() {
        return newsRepository.findAll();
    }

    public void saveNews(News news) {
        if (news == null) {
            throw new NullPointerException();
        }

        News newNews = new News();

        newNews.setTitle(news.getTitle());
        newNews.setDescription(news.getDescription());

        try {
            newsRepository.save(newNews);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void deleteNews(Long id) {
        try {
            newsRepository.deleteById(id);
        } catch (NoSuchElementException e){
            e.printStackTrace();
        }
    }
}

package com.example.socialnetwork.service;

import com.example.socialnetwork.models.News;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class NewsService {

//    private NewsRepository newsRepository;
//
//    @Autowired
//    public NewsService(NewsRepository newsRepository) {
//        this.newsRepository = newsRepository;
//    }
//
//    public List<News> getAllNews() {
//        return newsRepository.findAll();
//    }
//
//    public void saveNews(News news) {
//        if (news == null) {
//            throw new NullPointerException();
//        }
//
//        News newNews = new News();
//
//        try {
//            newsRepository.save(newNews);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//    public void deleteNews(Long id) {
//        try {
//            newsRepository.deleteById(id);
//        } catch (NoSuchElementException e){
//            e.printStackTrace();
//        }
//    }

    public List<News> parseAllNews() throws IOException {
        Document riaRu = Jsoup.connect("https://ria.ru/lenta/").maxBodySize(0).get();

        List<News> news = new ArrayList<>();
        List<String> imgsSrc = new ArrayList<>();

        Elements elements = riaRu.getElementsByClass("list-item");
        for(Element element : elements) {
            String title = element.getElementsByClass("list-item__title color-font-hover-only").text();
            String date = element.getElementsByClass("list-item__date").text();
            String link = "https://ria.ru" + element.select("a").attr("href");

            news.add(new News(title, date, link));
        }

        Elements imgs = riaRu.getElementsByClass("responsive_img m-list-img");
        for(Element element : imgs) {
            String img = element.absUrl("src");
            imgsSrc.add(img);
        }

        addImagesToNews(news, imgsSrc);
        return news;
    }

    private void addImagesToNews(List<News> allNews, List<String> imgs) {
        for(int i = 0; i < allNews.size(); i++) {
            allNews.get(i).setImg(imgs.get(i));
        }
    }
}

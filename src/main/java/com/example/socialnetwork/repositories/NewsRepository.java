package com.example.socialnetwork.repositories;

import com.example.socialnetwork.models.News;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NewsRepository extends JpaRepository<News, Long> {
    List<News> findAll();
//    News findByTitleAndAuthor_Name(String title, String authorName);
    void deleteById(Long id);
}

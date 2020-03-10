package com.example.socialnetwork.models;

import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

@Data
@ToString(of = {"title", "date", "img", "link"})
@EqualsAndHashCode(of = {"title", "date", "img", "link"})
@NoArgsConstructor
public class News {

    private String title;
    private String date;
    private String img;
    private String link;

    public News(String title, String date, String link) {
        this.title = title;
        this.date = date;
        this.link = link;
    }

    public News(String title, String date, String img, String link) {
        this.title = title;
        this.date = date;
        this.img = img;
        this.link = link;
    }
}

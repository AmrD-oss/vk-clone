package com.example.socialnetwork.models;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Data
@Entity
@Table(name = "news")
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@SequenceGenerator(name = "id_generator", sequenceName = "news_id")
public class News extends IdIdentity{

    @Column(nullable = false)
    @NotNull(message = "Добавьте заголовок!")
    private String title;

    @Column(nullable = false)
    @NotNull(message = "Добавьте текст!")
    private String description;

    @Column(nullable = false)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date dateOfCreation;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "author_id")
    private Author author;

    public News(@NotNull(message = "Добавьте заголовок!") String title, @NotNull(message = "Добавьте текст!") String description, Author author) {
        this.title = title;
        this.description = description;
        this.dateOfCreation = new Date();
        this.author = author;
    }
}

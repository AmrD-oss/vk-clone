package com.example.socialnetwork.models;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "news")
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@SequenceGenerator(name = "id_generator", sequenceName = "news_id")
public class News extends IdIdentity{

    @Column(nullable = false)
    @Size(min = 3, max = 100)
    private String title;

    @Column(nullable = false)
    @NotNull(message = "Добавьте текст!")
    private String description;

    @Column
    @DateTimeFormat(pattern = "HH:mm d MMM")
    private LocalDateTime dateOfCreation = LocalDateTime.now();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "author_id")
    private User user;

    public News(String title, String description, User user) {
        this.title = title;
        this.description = description;
        this.user = user;
    }
}

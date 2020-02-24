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
@Entity
@Table(name = "news")
@ToString(of = {"id","title","description","dateOfCreation","userEntity"})
@EqualsAndHashCode(of = {"id","title","description","dateOfCreation","userEntity"})
@NoArgsConstructor
@AllArgsConstructor
public class News {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    @Size(min = 3, max = 100)
    private String title;

    @Column(nullable = false)
    @NotNull(message = "Добавьте текст!")
    private String description;

    @Column
    private Date dateOfCreation = new Date();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "author_id")
    private UserEntity userEntity;

    public News(String title, String description, UserEntity userEntity) {
        this.title = title;
        this.description = description;
        this.userEntity = userEntity;
    }
}

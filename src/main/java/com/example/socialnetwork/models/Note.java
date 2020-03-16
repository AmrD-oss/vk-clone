package com.example.socialnetwork.models;


import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "note", schema = "public")
@Data
@ToString(of = {"id", "description", "likes", "date", "user"})
@EqualsAndHashCode(of = {"id", "description", "likes", "date", "user"})
@NoArgsConstructor
@AllArgsConstructor
public class Note {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private long likes;
    private String description;
    private LocalDateTime date = LocalDateTime.now();
    private String image;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity user;
}

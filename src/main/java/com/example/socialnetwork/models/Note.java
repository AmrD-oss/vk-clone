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

    private String description;
    private long likes;
    private LocalDateTime date;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity user;
}

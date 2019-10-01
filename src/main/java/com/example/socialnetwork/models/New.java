package com.example.socialnetwork.models;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "new")
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@SequenceGenerator(name = "id_generator", sequenceName = "new_id")
@SuppressWarnings({"WeakerAccess"})
public class New extends IdIdentity{

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private LocalDateTime dateOfCreation;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "author_id")
    private Author author;
}

package com.example.socialnetwork.models;


import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Data
@Entity
@Table(name = "author")
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@SequenceGenerator(name = "id_generator", sequenceName = "author_id")
@SuppressWarnings({"WeakerAccess"})
public class Author extends IdIdentity{

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String surname;

    @Column
    private Date dataOfBirth;

    @Column(nullable = false)
    private Boolean online;

    @OneToMany(mappedBy = "author", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<New> news;
}

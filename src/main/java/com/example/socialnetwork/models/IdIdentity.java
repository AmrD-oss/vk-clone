package com.example.socialnetwork.models;


import lombok.Data;

import javax.persistence.*;

@Data
@MappedSuperclass
@SuppressWarnings({"WeakerAccess"})
public class IdIdentity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "id_generator")
    private Long id;
}

package com.example.socialnetwork.models;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Data
@Entity
@Table(name = "chat")
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@SequenceGenerator(name = "id_generator", sequenceName = "chat_id")
public class Chat extends IdIdentity{

    @Column(nullable = false)
    private String name;
}

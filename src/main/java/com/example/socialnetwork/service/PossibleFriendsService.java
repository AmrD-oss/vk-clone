package com.example.socialnetwork.service;

import com.example.socialnetwork.models.UserEntity;
import com.example.socialnetwork.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PossibleFriendsService {

    private final UserRepository repository;

    @Autowired
    public PossibleFriendsService(UserRepository repository) {
        this.repository = repository;
    }
}
